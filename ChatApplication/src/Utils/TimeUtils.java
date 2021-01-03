package Utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rtanyildizi
 */
public class TimeUtils {
    /**
     * @return Şimdiki zamanı formatlanmış bir String olarak döndürür.
     */
    public static String getCurrentFormattedTime() {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedTime = time.format(formatter);
        return formattedTime;
    }
}
