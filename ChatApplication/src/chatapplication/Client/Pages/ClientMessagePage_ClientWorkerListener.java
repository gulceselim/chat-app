/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Client.ClientWorkerListener;
import chatapplication.Server.Message;
import chatapplication.Server.ServerClientSerializable;

/**
 *
 * @author Selim
 */
public class ClientMessagePage_ClientWorkerListener implements ClientWorkerListener{
    ClientMessagePage clientMessagePage;    

    public ClientMessagePage_ClientWorkerListener(ClientMessagePage clientMessagePage) {
        this.clientMessagePage = clientMessagePage;
    } 
    
    @Override
    public void onClientIdSent(String id) {
    }

    @Override
    public void onClientList(ServerClientSerializable[] clientList) {
        this.clientMessagePage.onClientList(clientList);
    }

    @Override
    public void onClientMessageSent(Message messageSentByUsername) {
        this.clientMessagePage.onClientMessageSent(messageSentByUsername);
    }

    @Override
    public void onClientLog(String connectMsg) {
        this.clientMessagePage.onClientLog(connectMsg);
    }
}
