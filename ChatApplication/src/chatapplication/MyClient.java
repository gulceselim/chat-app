package chatapplication;

import java.io.*;
import java.net.*;

public class MyClient {
    Socket socket;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public MyClient(String host, int port) throws IOException{
        socket = new Socket(host, port); 
    }
    
    public void sendMessageToServer(String message){
        try {
            DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(message.trim());
            dout.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
}
