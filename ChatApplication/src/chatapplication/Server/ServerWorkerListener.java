/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.ObjectOutputStream;

/**
 * ServerWorker'ı dinleyen elementler: Server
 * @author rtanyildizi
 */
public interface ServerWorkerListener  {
    void onClientDisconnect(String id, String username);
    void onClientSendMessage(String id, String message);
    void onClientChangeUsername(String id, String newUsername);
}
