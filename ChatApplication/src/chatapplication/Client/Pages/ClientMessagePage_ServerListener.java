/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Server.ServerClientModel;
import chatapplication.Server.ServerListener;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author Selim
 */
public class ClientMessagePage_ServerListener implements ServerListener{
    ClientMessagePage clientMessagePage;

    public ClientMessagePage_ServerListener(ClientMessagePage clientMessagePage) {
        this.clientMessagePage = clientMessagePage;
    }
    
    @Override
    public void onServerLog(String logMessage, Color color) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onNewUserList(List<ServerClientModel> model) {
        this.clientMessagePage.onNewUserList(model);
    }
    
}
