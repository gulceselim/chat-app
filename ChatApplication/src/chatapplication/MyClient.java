package chatapplication;

import java.io.*;
import java.net.*;

public class MyClient {
    Socket socket;
    DataOutputStream dos;

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public MyClient(String host, int port) throws IOException{
        socket = new Socket(host, port);
        this.dos = new DataOutputStream(socket.getOutputStream());
    }
    
    public void sendMessageToServer(String message){
        try {
            this.dos.writeUTF(message.trim());
            this.dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
    }
}
