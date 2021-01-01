package chatapplication;

import chatapplication.Exceptions.InvalidPortException;
import java.io.*;
import java.net.*;

public class MyServer extends Thread{
    int port;
    ServerSocket serverSocket;
    
    public MyServer() {
        
    }
    
    public MyServer(int port) throws IOException{
        this.port = port;
        serverSocket = new ServerSocket(this.port);
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getPort() {
        return port;
    }
  
    public static boolean checkPort(String portStr) throws InvalidPortException{
        if(portStr != null && !"".equals(portStr)){
            int portInt = Integer.parseInt(portStr);
            if(portInt >= 0 && portInt <= 65353){
                return true;
            }
        }
        throw new InvalidPortException();
    }

    @Override
    public void  run() {
        try {
            System.out.println("✅ Server has started successfully at " + "http://localhost:" + this.port);
            DataInputStream dis;
            while(true) {
                Socket s = this.serverSocket.accept();
                dis = new DataInputStream(s.getInputStream());
                String str = (String) dis.readUTF();
                System.out.println(str);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
   
   
}