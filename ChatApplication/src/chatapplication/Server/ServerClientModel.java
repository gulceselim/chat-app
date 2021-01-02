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
    private String address;
    private int port;
    private ServerWorker worker;
    
    /**
     * Yeni bir ServerClientModel nesnesi oluşturur.
     * Client'ı dinleyerek taleplerine yanıt verecek ServerWorker nesnesini 
     * oluşturur ve dinleme işlemeni başlatır.
     * 
     * @param dis Client tarafından gönderilen mesajların dinleneceği DataInputStream nesnesi
     * @param id Client modelinin benzersiz anahtarı
     * @param username Client tarafından belirtilen kullanıcı adı
     * @param address Client'ın bağlandığı local adres
     * @param port Client'ın bağlandığı port numarası
     * @throws IOException 
     * @author rtanyildizi
     */
    public ServerClientModel(DataInputStream dis, String id, String username, String address, int port) throws IOException {
        this.dis = dis;
        this.id = id;
        this.username = username;
        this.address = address;
        this.port = port;
        this.dis = dis;
        
        
        worker = new ServerWorker(dis);
        worker.listen();
    }
}
