/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rtanyildizi
 */
public class ClientEventHandler {
   List<ClientListener> clientListeners;

    public ClientEventHandler() {
        this.clientListeners = new ArrayList<>();
    }
    
    public void addListener(ClientListener listener) {
        this.clientListeners.add(listener);
    }
     
    public void emitCreatePage() {
       this.clientListeners.forEach((listener) -> {
           listener.onCreatePage();
       });
   } 
}
