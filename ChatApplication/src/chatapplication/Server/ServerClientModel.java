/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
/**
 *
 * @author rtanyildizi
 */
public class ServerClientModel {
    private DataInputStream dis;
    private ObjectOutputStream oos;

    public ObjectOutputStream getOos() {
        return oos;
    }
    private String id;
    private String username;
    private int port;
    private ServerWorker worker;
    private Color messageColor;
    
    /**
     * Yeni bir ServerClientModel nesnesi oluşturur.Client'ı dinleyerek taleplerine yanıt verecek ServerWorker nesnesini 
     * oluşturur ve dinleme işlemeni başlatır.
     * 
     * @param dis Client tarafından gönderilen mesajların dinleneceği DataInputStream nesnesi
     * @param username Client tarafından belirtilen kullanıcı adı
     * @param port Client'ın bağlandığı port numarası
     * @param listener Client disconnect yapınca çalışacak event
     * @throws IOException 
     * @author rtanyildizi
     */
    public ServerClientModel(DataInputStream dis, ObjectOutputStream oos, String id, String username, int port, Color messageColor, ServerWorkerListener listener) throws IOException {
        this.dis = dis;
        this.username = username;
        this.port = port;
        this.dis = dis;
        this.oos = oos;
        this.messageColor = messageColor;
        
        this.id = id;
        
        worker = new ServerWorker(this.dis, this.id, this.username);
        worker.getEventHandler().addServerWorkerListener(listener);
        worker.listen();
    }
    
    
    public ServerClientSerializable toSerializable() {
        return new ServerClientSerializable(this.id, this.username, this.port, this.messageColor);
    }

    public void setUsername(String username) {
        this.username = username;
        this.worker.setClientUsername(username);
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
