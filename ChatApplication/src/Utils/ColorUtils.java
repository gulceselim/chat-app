package Utils;

import java.awt.Color;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Selim
 */
public class ColorUtils {
    public static Color getRandomColor(){
        Random rand = new Random();
        int high = 120;
        int r = rand.nextInt(high);
        int g = rand.nextInt(high);
        int b = rand.nextInt(high);
        System.out.println(r + "," + g + "," + b);
        return new Color(r, g, b);
    }
}
