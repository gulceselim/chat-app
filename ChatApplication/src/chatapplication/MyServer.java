package chatapplication;

import java.io.*;
import java.net.*;

public class MyServer {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = new Socket();
            DataInputStream dis;
            while(true) {
                s = ss.accept();
                dis = new DataInputStream(s.getInputStream());
                String str = (String) dis.readUTF();
                System.out.println(str);
            }
            //ss.Close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}