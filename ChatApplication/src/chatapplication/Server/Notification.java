/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Selim
 */
public class Notification implements Serializable{
    String logMessage;

    Color logMessageColor;

    public Notification(String logMessage, Color logMessageColor) {
        this.logMessage = logMessage;
        this.logMessageColor = logMessageColor;
    }
    
    public String getLogMessage() {
        return logMessage;
    }

    public Color getLogMessageColor() {
        return logMessageColor;
    }
    
}
