/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */
public class ServerClientSerializable implements Serializable{
    String id;
    String username;
    int port;

    public ServerClientSerializable(String id, String username, int port) {
        this.id = id;
        this.username = username;
        this.port = port;
    }
    
    public static ServerClientSerializable[] fromServerClientModelList(List<ServerClientModel> clientModelList) {
        ServerClientSerializable[] array = new ServerClientSerializable[clientModelList.size()];
        
        for(int i = 0; i < clientModelList.size(); ++i) {
            array[i] = clientModelList.get(i).toSerializable();
        } 

        return array;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getPort() {
        return port;
    }
    
    
}
