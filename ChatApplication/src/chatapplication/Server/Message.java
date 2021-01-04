/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.Serializable;

/**
 *
 * @author Selim
 */
public class Message implements Serializable{
    ServerClientSerializable client;
    String message;


    public Message(ServerClientSerializable client, String message) {
        this.client = client;
        this.message = message;
    }

    Message(ServerClientSerializable clientSerializable, Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ServerClientSerializable getClient() {
        return client;
    }

    public String getMessage() {
        return message;
    }
    
    
    
}
