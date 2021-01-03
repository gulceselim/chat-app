package chatapplication.Client;

import chatapplication.Server.ServerListener;
import java.io.*;
import java.net.*;

/**
 * 
 * @author rtanyildizi
 */
public class Client {
    Socket socket;
    DataOutputStream dos;
    ObjectOutputStream oos;
    String username;
    String host;
    int port;
    
    /**
     * Yeni bir Client nesnesi oluşturur.
     * @param host Client'ın bağlanacağı host adresi.
     * @param port Client'ın bağlanacağı port değeri.
     * @param username Client'ın gönderdiği mesajların önüne eklenecek kullanıcı adı.
     * @author rtanyildizi
     */
    public Client(String host, int port, String username){
        this.username = username;
        this.host = host;
        this.port = port;
    }
    
    /**
     * Bağlanılan Server nesnesine bir talep gönderir.
     * @param message Gönderilecek talebin içerdiği mesaj.
     * @author rtanyildizi
     */
    private void sendDataToServer(String message){
        try {
            this.dos.writeUTF(message.trim());
            this.dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void sendObjectToServer(Object obj){
        try {
            this.oos.writeObject(obj);
            this.oos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Bağlı olan diğer Client'lara iletilmesi için Server'a
     * bir mesaj gönderir. Server bu mesajı bağlı olan tüm Client'lara iletir.
     * @param message Gönderilecek mesaj metni.
     * @author rtanyildizi
     */
    public void sendMessage(String message){
        this.sendDataToServer("/!m/%s/!e/".formatted(message));
    }
    
    /**
     * Server'a disconnect talebi gönderir.
     */
    public void disconnect() throws IOException  {
        final String disconnectMsg = "/!d//!e/";
        this.sendDataToServer(disconnectMsg);
        this.dos.close();
        this.socket.close();
    }
    
    /**
     * Server'a bağlanma talebi gönderir ve Server tarafından
     * eşsiz bir anahtar atanarak clientListt listesine eklenir.
     * @throws IOException 
     * @author rtanyildizi
     */
    public void connect() throws IOException{
        this.socket = new Socket(this.host, this.port);
        this.dos = new DataOutputStream(socket.getOutputStream());
        this.sendDataToServer("/!c/%s/!e/".formatted(this.username));
    }
    
    public void connectListenerToServer(ServerListener serverListener){
        this.sendObjectToServer(serverListener);
    }
    
    public void changeUsername(String newUsername){
        this.sendDataToServer("/!rn/%s/!e/".formatted(newUsername));
    }
}
