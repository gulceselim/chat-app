/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server.Pages;

import chatapplication.Server.ServerClientModel;
import chatapplication.Server.ServerListener;
import java.awt.Color;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */

class ServerLogPage_ServerListener implements ServerListener {

    ServerLogPage serverLogPage;

    public ServerLogPage_ServerListener(ServerLogPage serverLogPage) {
        this.serverLogPage = serverLogPage;
    }

    @Override
    public void onServerLog(String logMessage, Color color) {
        this.serverLogPage.onServerLog(logMessage, color);
    }

    @Override
    public void onNewUserList(List<ServerClientModel> model) {
        this.serverLogPage.onNewUserList(model);
    }
}