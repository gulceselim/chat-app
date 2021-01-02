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

    List<ServerClientModel> clientModels;

    List<ServerListener> serverListeners;

    /**
     * Belirtilen port üzerinde bir ServerSocket oluşturur
     *
     * @param port ServerSocket'in oluşturulacağı port
     * @throws IOException
     * @author rtanyildizi
     */
    public Server(int port) throws IOException {
        this.serverListeners = new ArrayList<>();
        this.clientModels = Collections.synchronizedList(new ArrayList<>());
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    /**
     * Server döngüsünün çalışması için bir thread oluşturur.
     * @author rtanyildizi
     */
    @Override
    public void run() {
        String serverMsg = "✔ Server has started successfully at http://%s:%d".formatted("localhost", this.port);
        this.emitServerLog(serverMsg, new Color(0, 0, 120));
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
        this.emitServerLog(dscMsg, new Color(180, 0, 0));    
    }

    
    /**
     * serverListeners listesine yeni bir server listener ekler.
     * @param listener Listeye eklenecek listener nesnesi.
     * @author rtanyildizi
     */
    public void addServerListener(ServerListener listener) {
        this.serverListeners.add(listener);
    }

    /**
     * Server log mesajını formatlar, mesaj ve renk bilgisini 
     * dinleyen tüm ServerListener'lara iletir.
     *
     * @param message İletilecek mesaj
     * @param color Mesajın görüntleneceği renk
     * @author rtanyildizi
     */
    private void emitServerLog(String message, Color color) {
        // Tüm ServerListener'lar için onMessage methodunu çalıştır.
        final String formattedMessage = "%s\n\r".formatted(message);
        serverListeners.forEach(listener -> {
            listener.onServerLog(formattedMessage, color);
        });
    }
    
    /**
     * Yeni bir Client bağlandığında, bu Client'a ait ServerClientModel nesnesini
     * dinleyen tüm serverListenerlara iletir.
     * @param model İletilecek ServerClientModel nesnesi
     */
    private void emitNewClient(ServerClientModel model) {
        serverListeners.forEach(listener -> {
            listener.onNewClient(model);
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
                    String message = dis.readUTF();

                    if (message.startsWith("/!c/") && message.endsWith("/!e/")) {
                        String commandTrimmed = message.split("/!c/")[1];
                        final String username = commandTrimmed.substring(0, commandTrimmed.length() - 4);

                        if (username != null && !"".equals(username)) {
                            final String id = UUID.randomUUID().toString();

                            ServerClientModel scm = new ServerClientModel(dis, id, username, s.getPort(), new Server_ServerWorkerListener(this));
                            this.clientModels.add(scm);

                            String connectMsg = "❤ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort());
                            
                            this.emitNewClient(scm);
                            this.emitServerLog(connectMsg, new Color(0, 120, 0));

                        } else {
                            String errorMsg = "✘ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort());
                            this.emitServerLog(errorMsg, new Color(120, 0, 0));
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
