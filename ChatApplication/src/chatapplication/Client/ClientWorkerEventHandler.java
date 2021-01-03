/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import chatapplication.Server.ServerClientModel;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Selim
 */
public class ClientWorkerEventHandler {
    List<ClientWorkerListener> clientWorkerListener;

    public ClientWorkerEventHandler() {
        this.clientWorkerListener = new ArrayList<>();
    }
    
    public void addWorkerClientListener(ClientWorkerListener clientListener){
        this.clientWorkerListener.add(clientListener);
    }
    
    public void emitClientIdSent(String id){
        clientWorkerListener.forEach((listener) -> {
            listener.onClientIdSent(id);
        });
    }
    
    public void emitClientList(ServerClientModel clientList){
        clientWorkerListener.forEach((listener) -> {
            listener.onClientList(clientList);
        });
    }
    
}
