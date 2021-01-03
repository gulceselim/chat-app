/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import chatapplication.Server.Packet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rtanyildizi
 */
public class ClientWorker {
    ObjectInputStream ois;
    boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ClientWorker(ObjectInputStream ois) {
        this.ois = ois;
        this.running = true;
    }
    
    
    public void listen() {
        new Thread(() -> {
            
            while(this.running) {
                try {
                    Packet pack = (Packet) ois.readObject();
                    packetProcessor(pack);
                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
        }, "listen").start();
    }
    
    private void packetProcessor(Packet packet) {
        if(packet.getObjName().equals("clientId")) {
            System.out.println("client id ataması için listener oluştur. id: " + packet.getObj().toString());
        }
    }
    
}
