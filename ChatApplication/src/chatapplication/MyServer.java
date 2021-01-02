package chatapplication;

import chatapplication.Exceptions.InvalidPortException;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyServer extends Thread{
    int port;
    String connectMsg, serverMsg, errorMsg;
    ServerSocket serverSocket;
    List<ServerClientModel> clientModels = Collections.synchronizedList(new ArrayList<>());
    ServerLogPage serverLogPage;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDateTime now;
    
    
    public MyServer() {
        
    }
    
    public MyServer(ServerLogPage serverLogPage, int port) throws IOException{
        this.port = port;
        this.serverLogPage = serverLogPage;
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
        now = LocalDateTime.now();
        this.serverMsg = "✅ Server has started successfully at http://%s:%d".formatted("localhost", this.port) + "\t\t\t%s".formatted(now.format(formatter));
        this.serverLogPage.tAreaLog.append(serverMsg + "\n");
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
                            now = LocalDateTime.now();
                            final String id = UUID.randomUUID().toString();
                            ServerClientModel scm = new ServerClientModel(dis, id, username, s.getInetAddress().getHostAddress(), s.getPort());
                            this.clientModels.add(scm);
                            this.connectMsg = "✅ %s connected to the server from /%s:%d".formatted(username, s.getInetAddress().getHostAddress(), s.getPort())  + "\t\t\t\t%s".formatted(now.format(formatter));
                            this.serverLogPage.tAreaLog.append(connectMsg + "\n");
                        } else {
                            this.errorMsg = "❗ There is a connection request from %s:%d with an invalid username".formatted(s.getInetAddress().getHostAddress(), s.getPort());
                            this.serverLogPage.tAreaLog.append(errorMsg + "\n");
                        }
                  }
              } catch (IOException ex) {
                  Logger.getLogger(MyServer.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
        }, "receive").start();
    }
}
        
      
    
   
   
