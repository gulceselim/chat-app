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
public interface ClientWorkerListener {
    void onClientIdSent(String id);
    void onClientList(List<ServerClientModel> clientList);
}
