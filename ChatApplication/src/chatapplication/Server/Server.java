package chatapplication.Server;

import Utils.ColorUtils;
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
        Notification notification = new Notification(serverMsg, new Color(0, 0, 200));
        this.eventHandler.emitServerLog(notification);
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
        Notification notification = new Notification(dscMsg, new Color(240, 0, 0));
        this.eventHandler.emitServerLog(notification);
        this.eventHandler.emitNewUserList(this.clientModels);
        this.sendClientList();
        this.sendClientLog(notification);
    }
    
    public void onClientSendMessage(String id, String message){
        var client = this.findClientModelById(id);
        if(client != null){
            String msg = "📨 Client %s sent message".formatted(client.getUsername());
            Notification notification = new Notification(msg, new Color(140, 0, 255));
            this.eventHandler.emitServerLog(notification);
            this.sendMessageToClients(client, message);
        }
    }
    
    public void onClientChangeUsername(String id, String newUsername){
            this.clientModels.forEach((model) -> {
                if(model.getId().equals(id)){
                    if(checkUsername(newUsername)){
                        final String oldUsername = model.getUsername();
                        model.setUsername(newUsername);
                        final String msg = "📣 Client %s changed his/her username to %s".formatted(oldUsername,newUsername);
                        Notification notification = new Notification(msg, new Color(255, 126, 0));
                        this.eventHandler.emitServerLog(notification);
                        this.eventHandler.emitNewUserList(clientModels);
                        this.sendClientList();
                        this.sendClientLog(notification);
                    }else{ 
                        sendUsernameErrorToClient(model.getOos());
                    }
                }
            });
        
    }
    
    public void onSendClientList() {
        this.sendClientList();
    }
    
    private void sendClientIdBack(ObjectOutputStream oos, String id) throws IOException {
        oos.writeObject(new Packet(id, "clientId"));
    }
    
    private void sendClientList() {
        ServerClientSerializable[] array = ServerClientSerializable.fromServerClientModelList(clientModels);
        sendPacketToAllClients(new Packet(array, "clientList"));
    }
    
    private void sendMessageToClients(ServerClientModel client, String message){
        var clientSerializable = client.toSerializable();
        Message messageObj = new Message(clientSerializable, message);
        sendPacketToAllClients(new Packet(messageObj, "clientMessage"));
        
    }
    
    private void sendClientLog(Notification notification){
        sendPacketToAllClients(new Packet(notification, "clientLog"));
    }
    
    private ServerClientModel findClientModelById(String id){
        ServerClientModel find = null;
        for (int i = 0; i < this.clientModels.size(); i++) {
            var clientModel = this.clientModels.get(i);
            if(clientModel.getId().equals(id)){
                    find = clientModel;
                }
        }
        return find;
    }
    
    
    private void sendPacketToAllClients(Packet packet){
        this.clientModels.forEach((client) -> {
            try {
                client.getOos().writeObject((packet));
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
    
    private void sendUsernameErrorToClient(ObjectOutputStream oos){
        try {
            oos.writeObject(new Packet(null, "usernameError"));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                        
                        if (username != null && !"".equals(username) && checkUsername(username)) {
                            final String id = UUID.randomUUID().toString();
                            final Color messageColor = ColorUtils.getRandomColor();
                            
                            ServerClientModel scm = new ServerClientModel(dis, oos, id, username, s.getPort(), messageColor, new Server_ServerWorkerListener(this));
                            this.clientModels.add(scm);
                            
                            this.sendClientIdBack(oos, id);
                            String connectMsg = "❤ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort());
                            
                            this.eventHandler.emitNewUserList(this.clientModels);
                            Notification notification = new Notification(connectMsg, new Color(0, 160, 0));
                            this.eventHandler.emitServerLog(notification);
                            this.sendClientLog(notification);
                        } else {
                            String errorMsg = "✘ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort());
                            Notification notification = new Notification(errorMsg, new Color(160, 0, 0));
                            this.eventHandler.emitServerLog(notification);
                            this.sendUsernameErrorToClient(oos);
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
    
    private boolean checkUsername(String username){
        for (int i = 0; i < clientModels.size(); i++) {
            if(clientModels.get(i).getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }
}
