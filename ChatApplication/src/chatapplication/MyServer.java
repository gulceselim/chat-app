package chatapplication;

import chatapplication.Exceptions.InvalidPortException;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer extends Thread{
    int port;
    ServerSocket serverSocket;
    List<ServerClientModel> clientModels = Collections.synchronizedList(new ArrayList<>());
    
    public MyServer() {
        
    }
    
    public MyServer(int port) throws IOException{
        this.port = port;
        this.serverSocket = new ServerSocket(this.port);
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
        System.out.println("✅ Server has started successfully at http://%s:%d".formatted("localhost", this.port));
        receive(this.serverSocket);
    }
    
    private void receive(ServerSocket serverSocket) {        
        new Thread(() -> {
            while(true) {
              try {
                  Socket s = serverSocket.accept();
                  DataInputStream dis = new DataInputStream(s.getInputStream());
                  String message = dis.readUTF();
                  System.out.println(message);
                  
                  if(message.startsWith("/!c/") && message.endsWith("/!e/")) {
                      String commandTrimmed = message.split("/!c/")[1];
                        final String username = commandTrimmed.substring(0, commandTrimmed.length() - 4);
                        
                        if(username != null && !"".equals(username)){
                            final String id = UUID.randomUUID().toString();
                            ServerClientModel scm = new ServerClientModel(dis, id, username, s.getInetAddress().getHostAddress(), s.getPort());
                            clientModels.add(scm);
                            System.out.println("✅ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort()));
                        } else {
                            System.out.println("❗ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort()));
                        }
                  }
              } catch (IOException ex) {
                  Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        }, "receive").start();
    }
}
        
      
    
   
   
