package chatapplication;

import java.io.*;
import java.net.*;

public class MyClient {
    Socket socket;
    DataOutputStream dos;
    String username;
    String host;
    int port;
    
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public Socket getSocket() {
        return socket;
    }
    
    public MyClient(String host, int port, String clientUsername){
        this.username = clientUsername;
        this.host = host;
        this.port = port;
    }
    
    private void sendRequestToServer(String message){
        try {
            this.dos.writeUTF(message.trim());
            this.dos.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void sendMessage(String message) throws IOException{
        this.socket = new Socket(this.host, this.port);
        this.sendRequestToServer("/!m/%s/!e/".formatted(message));
    }
    
    public void connect() throws IOException{
        this.socket = new Socket(this.host, this.port);
        this.sendRequestToServer("/!c/%s/!e/".formatted(this.username));
        this.dos = new DataOutputStream(socket.getOutputStream());
    }
}
