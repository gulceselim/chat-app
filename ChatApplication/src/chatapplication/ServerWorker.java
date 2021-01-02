/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rtanyildizi
 */
class ServerWorker {
    DataInputStream dis;
    
    public ServerWorker(DataInputStream dis){
        this.dis = dis;
    }
    
    public void listen(){
        new Thread(() -> {
                try {
                    String message =  "";
                    while(true) {
                        message = dis.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException ex) {
                        Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
        }, "listen").start(); 
        
    }
}
