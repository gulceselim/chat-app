package chatapplication.Server;

import chatapplication.Server.Exceptions.InvalidPortException;
import java.awt.Color;
import java.io.*;
import java.net.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rtanyildizi
 */
public class Server extends Thread {

    int port;
    ServerSocket serverSocket;
    DateTimeFormatter formatter;
    ServerEventHandler eventHandler;
    List<ServerClientModel> clientModels;
    
    public ServerEventHandler getEventHandler() {
        return eventHandler;
    }


    /**
     * Belirtilen port üzerinde bir ServerSocket oluşturur
     *
     * @param port ServerSocket'in oluşturulacağı port
     * @throws IOException
     * @author rtanyildizi
     */
    public Server(int port) throws IOException {
        this.port = port;
        this.clientModels = Collections.synchronizedList(new ArrayList<>());
        this.serverSocket = new ServerSocket(this.port);
        this.eventHandler = new ServerEventHandler();
    }

    /**
     * Server döngüsünün çalışması için bir thread oluşturur.
     * @author rtanyildizi
     */
    @Override
    public void run() {
        String serverMsg = "✔ Server has started successfully at http://%s:%d".formatted("localhost", this.port);
        this.eventHandler.emitServerLog(serverMsg, new Color(0, 0, 120));
        receive();
    }
    
    
    /**
     * ServerWorker tarafından disconnect eventi oluşturulduğunda çalışan fonksiyondur.
     * @param clientId Bağlantısı kesilecek Client'ın benzersiz anahtarı
     * @param clientUsername Bağlantısı kesilecek Cleint'ın kullanıcı adı
     * @author rtanyildizi
     */
    public void onClientDisconnect(String clientId, String clientUsername) {
        this.clientModels.removeIf((model) -> (model.getId().equals(clientId)));
        String dscMsg = "💔 Client %s disconnected from the server".formatted(clientUsername);
        this.eventHandler.emitServerLog(dscMsg, new Color(180, 0, 0));
        this.eventHandler.emitNewUserList(this.clientModels);
    }
    
    public void onClientSendMessage(String username){
        String msg = "📨 Client %s sent message".formatted(username);
        this.eventHandler.emitServerLog(msg, new Color(180, 30, 110));
    }
    
    public void onClientChangeUsername(String id, String newUsername){
        this.clientModels.forEach((model) -> {
            if(model.getId().equals(id)){
                final String oldUsername = model.getUsername();
                model.setUsername(newUsername);
                final String msg = "📣 Client %s changed his/her username to %s".formatted(oldUsername,newUsername);
                this.eventHandler.emitServerLog(msg, new Color(120, 30, 180));
                this.eventHandler.emitNewUserList(clientModels);
            }
        });
    }
    
    private void sendClientIdBack(ObjectOutputStream oos, String id) throws IOException {
        oos.writeObject(new Packet(id, "clientId"));
    }
    
    private void sendClientList() throws IOException {
        ServerClientSerializable[] array = ServerClientSerializable.fromServerClientModelList(clientModels);
        
        clientModels.forEach((client) -> {
            try {
                client.getOos().writeObject(new Packet(array, "clientList"));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
   
    /**
     * Client'lar tarafından ServerSocket'a yapılan bağlantı taleplerini kabul
     * etmek üzere bir thread oluşturur. Eğer bağlantı talebiyle gönderilen
     * komut gerekli şartları sağlıyorsa, talebi gönderen Client için bir
     * ClientServerModel nesnesi oluşturur ve oluşturulan bu nesneyi clientList
     * listesine ekler.
     */
    private void receive() {
        new Thread(() -> {
            while (true) {
                try {
                    Socket s = this.serverSocket.accept();
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());

                    String message = dis.readUTF();

                    if (message.startsWith("/!c/") && message.endsWith("/!e/")) {
                        String commandTrimmed = message.split("/!c/")[1];
                        final String username = commandTrimmed.substring(0, commandTrimmed.length() - 4);

                        if (username != null && !"".equals(username)) {
                            final String id = UUID.randomUUID().toString();
                            
                            ServerClientModel scm = new ServerClientModel(dis, oos, id, username, s.getPort(), new Server_ServerWorkerListener(this));
                            this.clientModels.add(scm);
                            this.sendClientIdBack(oos, id);

                            String connectMsg = "❤ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort());
                            this.sendClientList();
                            
                            this.eventHandler.emitNewUserList(this.clientModels);
                            this.eventHandler.emitServerLog(connectMsg, new Color(0, 120, 0));

                        } else {
                            String errorMsg = "✘ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort());
                            this.eventHandler.emitServerLog(errorMsg, new Color(120, 0, 0));
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, "receive").start();
    }

    /**
     * Server nesnesinin bağlanması istenen port değerinin geçerli olup
     * olmadığını kontrol eder.
     *
     * @param portStr
     * @return Belirtilen port değeri geçerli ise true döndürür.
     * @throws InvalidPortException Belirtilen port değeri geçersiz ise
     * InvalidPortException hatası oluşturulur.
     */
    public static boolean checkPort(String portStr) throws InvalidPortException {
        if (portStr != null && !"".equals(portStr)) {
            int portInt = Integer.parseInt(portStr);
            if (portInt >= 0 && portInt <= 65353) {
                return true;
            }
        }
        throw new InvalidPortException();
    }
}
