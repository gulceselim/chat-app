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
public interface ClientWorkerListener {
    void onClientIdSent(String id);
    void onClientList(ServerClientSerializable[] clientList);
    void onClientMessageSent(Message messageSentByUsername);
    void onClientLog(Notification notification);
}
