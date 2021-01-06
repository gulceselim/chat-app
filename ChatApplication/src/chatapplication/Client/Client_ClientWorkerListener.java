/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import chatapplication.Server.Message;
import chatapplication.Server.Notification;
import chatapplication.Server.ServerClientSerializable;

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
    public void onClientList(ServerClientSerializable[] clientList) {
    }

    @Override
    public void onClientMessageSent(Message messageSentByUsername) {
    }

    @Override
    public void onClientLog(Notification notification) {
    }

}
