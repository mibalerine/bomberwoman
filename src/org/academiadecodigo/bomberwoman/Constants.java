package org.academiadecodigo.bomberwoman;

/**
 * Created by miro on 06/11/2017.
 */
public class Constants {

    public static final String BRICK_CHAR = "#";
    public static final String PLAYER_CHAR = "x";

    public static String VERTICAL_CHAR = "▓";
    public static String CORNER_CHAR = "▓";
    public static String HORIZONTAL_CHAR = "▓";

    public static String BAR_LIMIT_CHAR = "▓";
    public static String OBJECT_BOMB = "δ";
    public static String OBJECT_FLAME = "Θ";
    public static int CMD_QUIT = 3;

    public static int PORT = 8080;

    static void setCharMode(boolean charMode) {

        if(charMode) {

            VERTICAL_CHAR = "|";
            CORNER_CHAR = "+";
            HORIZONTAL_CHAR = "-";
            BAR_LIMIT_CHAR = "#";
        }
    }
}
