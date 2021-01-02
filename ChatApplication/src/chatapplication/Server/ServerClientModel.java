/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author rtanyildizi
 */
public class ServerClientModel {
    private DataInputStream dis;
    private String id;
    private String username;
    private int port;
    private ServerWorker worker;
    
    /**
     * Yeni bir ServerClientModel nesnesi oluşturur.Client'ı dinleyerek taleplerine yanıt verecek ServerWorker nesnesini 
     * oluşturur ve dinleme işlemeni başlatır.
     * 
     * @param dis Client tarafından gönderilen mesajların dinleneceği DataInputStream nesnesi
     * @param id Client modelinin benzersiz anahtarı
     * @param username Client tarafından belirtilen kullanıcı adı
     * @param port Client'ın bağlandığı port numarası
     * @param onDisconnect Client disconnect yapınca çalışacak event
     * @throws IOException 
     * @author rtanyildizi
     */
    public ServerClientModel(DataInputStream dis, String id, String username, int port, ServerWorkerListener onDisconnect) throws IOException {
        this.dis = dis;
        this.id = id;
        this.username = username;
        this.port = port;
        this.dis = dis;
        
        
        worker = new ServerWorker(this.dis, this.id, this.username);
        worker.addServerWorkerListener(onDisconnect);
        worker.listen();
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }

    public String getId() {
        return id;
    }

}
