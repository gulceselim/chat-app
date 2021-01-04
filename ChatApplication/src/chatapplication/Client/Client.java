package chatapplication.Client;

import java.io.*;
import java.net.*;

/**
 *
 * @author rtanyildizi
 */
public class Client {

    ClientWorker worker;
    DataOutputStream dos;
    Socket socket;
    String username;
    String host;
    String id;
    String message;
    ClientEventHandler eventHandler;
    int port;

    /**
     * Yeni bir Client nesnesi oluşturur.
     *
     * @param host Client'ın bağlanacağı host adresi.
     * @param port Client'ın bağlanacağı port değeri.
     * @param username Client'ın gönderdiği mesajların önüne eklenecek kullanıcı
     * adı.
     */
    public Client(String host, int port, String username) {
        this.username = username;
        this.host = host;
        this.port = port;
        this.eventHandler = new ClientEventHandler();
    }

    /**
     * GETTERS AND SETTERS *
     */
    
    
    
    public ClientWorkerEventHandler getWorkerEventHandler() {
        return this.worker.getClientWorkerEventHandler();
    }
    
      public ClientEventHandler getEventHandler() {
        return eventHandler;
    }


    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

  

    /**
     * Server'a bağlanma talebi gönderir ve Server tarafından 
     * eşsiz bir anahtar atanarak clientListt listesine eklenir.
     * Server'ı dinlemek üzere bir ClientWorker oluşturur.
     * 
     * @throws IOException
     */
    public void connect() throws IOException {
        this.socket = new Socket(this.host, this.port);
        this.dos = new DataOutputStream(socket.getOutputStream());
        Client_ClientWorkerListener listener = new Client_ClientWorkerListener(this);

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        this.worker = new ClientWorker(ois, listener);
        this.worker.listen();

        this.sendDataToServer("/!c/%s/!e/".formatted(this.username));
    }

    /**
     * Server'a disconnect talebi gönderir.
     * CleintWorker'ın çalışmasını durdurur.
     * TODO: "Socket is closed" hatasını çöz.
     * 
     */
    public void disconnect() throws IOException {
        final String disconnectMsg = "/!d//!e/";
        this.worker.setRunning(false);
        this.sendDataToServer(disconnectMsg);
    }

    /**
     * Bağlı olan diğer Client'lara iletilmesi için Server'a bir mesaj gönderir.
     * Server bu mesajı bağlı olan tüm Client'lara iletir.
     *
     * @param message Gönderilecek mesaj metni.
     */
    public void sendMessage(String message) {
        this.sendDataToServer("/!m/%s/!e/".formatted(message));
    }
    
    /**
     * Kullanıcı adını değiştirmek üzere Server'a bir talep iletir.
     * @param newUsername Yeni kullanıcı adı.
     * 
     */
    public void changeUsername(String newUsername) {
        this.sendDataToServer("/!rn/%s/!e/".formatted(newUsername));
    }
    
    /**
     * Server'dan var olan tüm Client'ların listesini talep eder.
     */
    public void getAllClients() {
        this.sendDataToServer("/!all//!e/");
    }
 
    
    /**
     * LISTENER FONKSIYONLARI
     */

    
    /**
     * Server tarafından gönderilen id'yi set eder.
     * @param id 
     */
    public void onClientIdSent(String id) {
        this.id = id;
        this.eventHandler.emitCreatePage();
    }


    /**
     * Bağlanılan Server nesnesine bir talep gönderir.
     *
     * @param message Gönderilecek talebin içerdiği mesaj.
     */
    private void sendDataToServer(String message) {
        try {
            this.dos.writeUTF(message.trim());
            this.dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
