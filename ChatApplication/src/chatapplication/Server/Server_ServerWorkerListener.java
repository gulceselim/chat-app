/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;


/**
 *
 * @author rtanyildizi
 */
class Server_ServerWorkerListener implements ServerWorkerListener  {
    Server server;
    
    public Server_ServerWorkerListener(Server server) {
        this.server = server;
    }
    @Override
    public void onClientDisconnect(String id, String username) {
        this.server.onClientDisconnect(id, username);
    }

    @Override
    public void onClientSendMessage(String username) {
        this.server.onClientSendMessage(username);
    }

    @Override
    public void onClientChangeUsername(String id, String newUsername) {
        this.server.onClientChangeUsername(id, newUsername);
    }
    
}