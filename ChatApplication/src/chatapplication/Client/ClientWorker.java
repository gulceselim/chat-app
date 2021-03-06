/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import chatapplication.Server.Message;
import chatapplication.Server.Notification;
import chatapplication.Server.Packet;
import chatapplication.Server.ServerClientSerializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rtanyildizi
 */
public class ClientWorker {
    ObjectInputStream ois;
    ClientWorkerEventHandler clientWorkerEventHandler;

    public ClientWorkerEventHandler getClientWorkerEventHandler() {
        return clientWorkerEventHandler;
    }
    boolean running;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public ClientWorker(ObjectInputStream ois, Client_ClientWorkerListener listener) {
        this.ois = ois;
        this.running = true;
        clientWorkerEventHandler = new ClientWorkerEventHandler();
        clientWorkerEventHandler.addWorkerClientListener(listener);
    }
    
    public void listen() {
        new Thread(() -> {
            while(this.running) {
                try {
                    Packet pack = (Packet) ois.readObject();
                    packetProcessor(pack);
                } catch (SocketException ex) {
                    return; 
                } 
                catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(ClientWorker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }, "listen").start();
    }
    
    private void packetProcessor(Packet packet) {
        switch (packet.getObjName()) {
            case "clientId" -> this.clientWorkerEventHandler.emitClientIdSent((String)packet.getObj());
            case "clientList" -> {
                this.clientWorkerEventHandler.emitClientList((ServerClientSerializable[])packet.getObj());
            }
            case "clientMessage" -> this.clientWorkerEventHandler.emitClientMessageSent((Message) packet.getObj());
            case "clientLog" -> this.clientWorkerEventHandler.emitClientLog((Notification) packet.getObj());
            case "usernameError" -> this.clientWorkerEventHandler.emitUsernameError();
            default -> {
            }
        }
    }
    
}
