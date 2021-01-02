package chatapplication;

import chatapplication.Exceptions.InvalidPortException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer extends Thread{
    int port;
    ServerSocket serverSocket;
    List<String> list = Collections.synchronizedList(new ArrayList<>());
    
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
        System.out.println("✅ Server has started successfully at " + "http://localhost:" + this.port);
        receive(this.serverSocket);
    }
    
    private void receive(ServerSocket serverSocket) {        
        new Thread(() -> {
            DataInputStream dis;
            while(true) {
              try {
                  Socket s = serverSocket.accept();
                  dis = new DataInputStream(s.getInputStream());
                  String str = (String) dis.readUTF();
                  
                  // TODO: Create server worker
                  while(!str.equals("exit")) {
                      System.out.println(str);
                      str = (String) dis.readUTF();
                  }
                  
                  
              } catch (IOException ex) {
                  Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        }, "receive").start();
    }
}
        
      
    
   
   
