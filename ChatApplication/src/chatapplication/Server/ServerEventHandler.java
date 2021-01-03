/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */
public class ServerEventHandler {
    List<ServerListener> serverListeners;

    public ServerEventHandler() {
        this.serverListeners = new ArrayList<>();
    }
    
    /**
     * serverListeners listesine yeni bir server listener ekler.
     * @param listener Listeye eklenecek listener nesnesi.
     * @author rtanyildizi
     */
    public void addServerListener(ServerListener listener) {
        this.serverListeners.add(listener);
    }

    /**
     * Server log mesajını formatlar, mesaj ve renk bilgisini 
     * dinleyen tüm ServerListener'lara iletir.
     *
     * @param message İletilecek mesaj
     * @param color Mesajın görüntleneceği renk
     * @author rtanyildizi
     */
    public void emitServerLog(String message, Color color) {
        // Tüm ServerListener'lar için onMessage methodunu çalıştır.
        final String formattedMessage = "%s\n\r".formatted(message);
        serverListeners.forEach(listener -> {
            listener.onServerLog(formattedMessage, color);
        });
    }
    
    /**
     * Yeni bir Client bağlandığında, bu Client'a ait ServerClientModel nesnesini
     * dinleyen tüm serverListenerlara iletir.
     * @param model İletilecek ServerClientModel nesnesi
     */
    public void emitNewClient(ServerClientModel model) {
        serverListeners.forEach(listener -> {
            listener.onNewClient(model);
        });
    }
    
}
