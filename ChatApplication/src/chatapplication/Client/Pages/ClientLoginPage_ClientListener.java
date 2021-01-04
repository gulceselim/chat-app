/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Client.Pages;

import chatapplication.Client.ClientListener;

/**
 *
 * @author rtanyildizi
 */
public class ClientLoginPage_ClientListener implements ClientListener {
    ClientLoginPage clientLoginPage;

    public ClientLoginPage_ClientListener(ClientLoginPage clientLoginPage){
        this.clientLoginPage = clientLoginPage;
    }
    
    
    
    @Override
    public void onCreatePage() {
        this.clientLoginPage.onCreatePage();
    }
    
}
