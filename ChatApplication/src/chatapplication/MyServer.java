package chatapplication;

import java.io.*;
import java.net.*;

public class MyServer extends Thread{
    String host;
    int port;
    
    public MyServer() {
        
    }
    
    public MyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return port;
    }
  

    @Override
    public void  run() {
        try {
            ServerSocket ss = new ServerSocket(this.port);
            System.out.println("✅ Server has started successfully at " + "http://" + this.host + ":" + this.port);
            DataInputStream dis;
            while(true) {
                Socket s = ss.accept();
                dis = new DataInputStream(s.getInputStream());
                String str = (String) dis.readUTF();
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
   
   
}