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
    Socket socket;
    
    public ServerWorker(Socket socket){
        this.socket = socket;
    }
    
    public void listen(){
        new Thread(() -> {
                try {
                    String message =  "";
                    while(true) {
                        dis = new DataInputStream(this.socket.getInputStream());
                        message = dis.readUTF();
                    }
                } catch (IOException ex) {
                        Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
        }, "listen").start(); 
        
    }
}
