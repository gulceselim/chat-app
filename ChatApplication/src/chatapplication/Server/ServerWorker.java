/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rtanyildizi
 */
public class ServerWorker {

    DataInputStream dis;
    String clientId;
    String clientUsername;
    ServerWorkerEventHandler eventHandler;
    boolean running;

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public ServerWorkerEventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * @param dis Client tarafından gönderilen mesajların dinlenileceği
     * DataInputStream nesnesi
     * @author rtanyildizi
     * @param oos
     * @param clientId
     * @param clientUsername
     */
    public ServerWorker(DataInputStream dis, String clientId, String clientUsername) {
        this.dis = dis;
        this.clientId = clientId;
        this.clientUsername = clientUsername;
        this.running = true;
        this.eventHandler = new ServerWorkerEventHandler();
    }

    /**
     * Belirtilen DataInputStream üzerinden Client'ın gönderdiği mesajları
     * dinlemek için bir thread oluşturur.
     *
     * @author rtanyildizi
     */
    public void listen() {
        new Thread(() -> {
            try {
                String message = "";
                while (this.running) {
                    message = dis.readUTF();
                    this.messageProcessor(message);
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }, "listen").start();

    }

    /**
     * ServerWorker nesnesinin çalışmasını durdurur.
     */
    private void stopWorker() {
        this.running = false;
        this.eventHandler.emitClientDisconnect(this.clientId, this.clientUsername);
    }

    private void messageProcessor(String message) {
        if (message.startsWith("/!d/") && message.endsWith("/!e/")) {
            this.stopWorker();
        } else if (message.startsWith("/!m/") && message.endsWith("/!e/")) {
            final String messageContent = message.substring(4, message.length() - 4);
            this.eventHandler.emitClientSendMessage(this.clientId, messageContent);
        } else if (message.startsWith("/!rn/") && message.endsWith("/!e/")) {
            String username = message.substring(5, message.length() - 4);
            if (username != null && !"".equals(username)) {
                this.eventHandler.emitClientChangeUsername(this.clientId, username);
            }
        } else if(message.equals("/!all//!e/")) {
            this.eventHandler.emitSendClientList();
        }
    }
}
