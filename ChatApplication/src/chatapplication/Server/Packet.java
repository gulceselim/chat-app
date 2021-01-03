/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapplication.Server;

import java.io.Serializable;

/**
 *
 * @author rtanyildizi
 */
public class Packet  implements Serializable {
    private Object obj;
    private String objName;
    
    /**
     * 
     * @param obj Gönderilecek nesne
     * @param objName Gönderilecek nesnenin ayırt edilebilmesini sağlayan isim
     * @param objClass Gönderilecek nesnenin class bilgisi
     */
    public Packet(Object obj, String objName) {
        this.obj = obj;
        this.objName = objName;
    }

    public String getObjName() {
        return objName;
    }

    public Object getObj() {
        return obj;
    }
}
