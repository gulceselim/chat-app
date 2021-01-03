/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatApplication.ServerWorker;

/**
 * ServerWorker'ı dinleyen elementler: Server
 * @author rtanyildizi
 */
public interface ServerWorkerListener  {
    void onClientDisconnect(String id, String username);
}
