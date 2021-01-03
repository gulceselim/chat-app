/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.awt.Color;
import java.util.List;

/**
 * Server'ı dinleyen elementler
 * @author rtanyildizi
 */
public interface ServerListener  {
    void onServerLog(String logMessage, Color color);
    void onNewUserList(List<ServerClientModel> model);
}



