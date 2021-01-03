/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Client.ClientWorkerListener;
import chatapplication.Server.ServerClientModel;
import java.util.List;

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
        return;
    }

    @Override
    public void onClientList(List<ServerClientModel> clientList) {
        this.clientMessagePage.onClientList(clientList);
    }
}
