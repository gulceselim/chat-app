package chatapplication.Server;

import chatapplication.Server.Exceptions.InvalidPortException;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
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
public class Server extends Thread{
    int port;
    ServerSocket serverSocket;
    DateTimeFormatter formatter;
    
    List<ServerClientModel> clientModels;
    
    List<ServerListener> serverListeners;
    
    /**
     * Belirtilen port üzerinde bir ServerSocket oluşturur
     * @param port ServerSocket'in oluşturulacağı port
     * @throws IOException
     * @author rtanyildizi
     */
    public Server(int port) throws IOException{
        this.serverListeners = new ArrayList<>();
        this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.clientModels = Collections.synchronizedList(new ArrayList<>());
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
    }

    /**
     * Server döngüsünün çalışması için bir thread oluşturur.
     */
    @Override
    public void  run() {
        String serverMsg = "✔ Server has started successfully at http://%s:%d".formatted("localhost", this.port);
        this.emitMessage(serverMsg);
        receive();
    }
    
    /**
     * serverListeners listesine yeni bir server listener ekler.
     * @param listener Listeye eklenecek listener nesnesi.
     */
    public void addServerListener(ServerListener listener) {
        System.out.println("new listener");
        this.serverListeners.add(listener);
    }
    
    /**
     * Server mesajının başına zaman bilgisi ekler 
     * ve mesajı dinleyen tüm ServerListener'lara iletir.
     * NOT: Her mesaja tek tek zaman bilgisi eklenmesine gerek kalmaz.
     * @param message İletilecek mesaj
     * @author rtanyildizi
     */
    private void emitMessage(String message) {
        final var now = LocalDateTime.now();
        
        // Tüm ServerListener'lar için onMessage methodunu çalıştır.
        final String messageWithTime = "%s ▶ %s".formatted(now.format(formatter),message);
        System.out.println(messageWithTime);
        serverListeners.forEach(listener -> {
            listener.onMessage(messageWithTime);
        });
    }
    
    /**
     * Client'lar tarafından ServerSocket'a yapılan bağlantı taleplerini kabul etmek üzere bir thread oluşturur.
     * Eğer bağlantı talebiyle gönderilen komut gerekli şartları sağlıyorsa, talebi gönderen Client için bir
     * ClientServerModel nesnesi oluşturur ve oluşturulan bu nesneyi clientList listesine ekler.
     */
    private void receive() {
        new Thread(() -> {
            while(true) {
              try {
                  Socket s = this.serverSocket.accept();
                  DataInputStream dis = new DataInputStream(s.getInputStream());
                  String message = dis.readUTF();
                  System.out.println(message);
                  
                  if(message.startsWith("/!c/") && message.endsWith("/!e/")) {
                      String commandTrimmed = message.split("/!c/")[1];
                        final String username = commandTrimmed.substring(0, commandTrimmed.length() - 4);
                        
                        if(username != null && !"".equals(username)){
                            final String id = UUID.randomUUID().toString();
                            ServerClientModel scm = new ServerClientModel(dis, id, username, s.getInetAddress().getHostAddress(), s.getPort());
                            this.clientModels.add(scm);
                            String connectMsg = "✔ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort());
                            this.emitMessage(connectMsg);
                        } else {
                            String errorMsg = "✘ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort());
                            this.emitMessage(errorMsg);
                        }
                  }
              } catch (IOException ex) {
                  Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        }, "receive").start();
    }
    
    /**
     * Server nesnesinin bağlanması istenen port değerinin geçerli olup olmadığını kontrol eder.
     * @param portStr
     * @return Belirtilen port değeri geçerli ise true döndürür.
     * @throws InvalidPortException Belirtilen port değeri geçersiz ise InvalidPortException hatası oluşturulur.
     */
    public static boolean checkPort(String portStr) throws InvalidPortException{
        if(portStr != null && !"".equals(portStr)){
            int portInt = Integer.parseInt(portStr);
            if(portInt >= 0 && portInt <= 65353){
                return true;
            }
        }
        throw new InvalidPortException();
    }
}
        
      
    
   
   
