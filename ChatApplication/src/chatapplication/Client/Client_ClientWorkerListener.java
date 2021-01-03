/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import chatapplication.Server.ServerClientModel;
import java.util.List;

/**
 *
 * @author Selim
 */
public class Client_ClientWorkerListener implements ClientWorkerListener{
    Client client;

    public Client_ClientWorkerListener(Client client) {
        this.client = client;
    }
    
    @Override
    public void onClientIdSent(String id) {
        this.client.onClientIdSent(id);
    }

    @Override
    public void onClientList(ServerClientModel clientList) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
