package org.academiadecodigo.bomberwoman;

/**
 * Created by codecadet on 06/11/17.
 */
public class Utils {

    public static int clamp(int value, int min, int max) {

        if (value < min) {

            value = min;
        }

        if (value > max) {

            value = max;
        }

        return value;
    }
}
