/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */
public class ServerWorkerEventHandler {
    List<ServerWorkerListener> serverWorkerListeners;
        
    public ServerWorkerEventHandler() {
        this.serverWorkerListeners = new ArrayList<>();
    }

    public void addServerWorkerListener(ServerWorkerListener listener) {
        this.serverWorkerListeners.add(listener);
    }
    
    public void emitClientDisconnect(String clientId, String clientUsername) {
        this.serverWorkerListeners.forEach((listener) -> {
            listener.onClientDisconnect(clientId, clientUsername);
        });
    }
    
    public void emitClientSendMessage(String username){
        this.serverWorkerListeners.forEach((listener) -> {
            listener.onClientSendMessage(username);
        });
    }
    
    public void emitClientChangeUsername(String id, String newUsername){
        this.serverWorkerListeners.forEach((listener) -> {
            listener.onClientChangeUsername(id,newUsername);
        });
    }
    

}
