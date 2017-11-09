package org.academiadecodigo.bomberwoman;

/**
 * Created by miro on 06/11/2017.
 */
public class Constants {

    public static final int QUIT_GAME = -100;
    public static final int NO_LEVEL_CREATED = -1;

    public static final String BRICK_CHAR = "#";
    public static final String PLAYER_CHAR = "x";
    public static final String WALL_CHAR = "▓";

    public static String VERTICAL_CHAR = "▓";
    public static String CORNER_CHAR = "▓";
    public static String HORIZONTAL_CHAR = "▓";

    public static String BAR_LIMIT_CHAR = "▓";

    public static String OBJECT_CONTROL_MENU = ">";
    public static String OBJECT_BOMB = "δ";
    public static String OBJECT_FLAME = "Θ";

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
