/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Client.ClientWorkerListener;
import chatapplication.Server.Message;
import chatapplication.Server.Notification;
import chatapplication.Server.ServerClientSerializable;

/**
 *
 * @author Selim
 */
public class ClientLoginPage_ClientWorkerListener implements ClientWorkerListener{
    ClientLoginPage clientLoginPage;

    public ClientLoginPage_ClientWorkerListener(ClientLoginPage clientLoginPage) {
        this.clientLoginPage = clientLoginPage;
    }
    
    @Override
    public void onClientIdSent(String id) {
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

    @Override
    public void onUsernameError() {
        this.clientLoginPage.onUsernameError();
    }
    
}
