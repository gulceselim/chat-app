/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Exceptions;

/**
 *
 * @author Selim
 */
public class InvalidPortException extends Exception{
    @Override
    public String getMessage(){
        return "InvalidPortError";
    }
}
