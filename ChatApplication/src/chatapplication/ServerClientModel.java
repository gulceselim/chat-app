/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author rtanyildizi
 */
public class ServerClientModel {
    private String id;
    private String username;
    private String address;
    private int port;
    private ServerWorker worker;
    private DataInputStream dis;
    
    
    public ServerClientModel(DataInputStream dis, String id, String username, String address, int port) throws IOException {
        this.dis = dis;
        this.id = id;
        this.username = username;
        this.address = address;
        this.port = port;
        
        
        worker = new ServerWorker(dis);
        worker.listen();
    }
}
