package org.academiadecodigo.bomberwoman;

/**
 * Created by miro on 06/11/2017.
 */
public class Constants {

    public static final int QUIT_GAME = -100;
    public static final int NO_LEVEL_CREATED = -1;

    public static final String BRICK_CHAR = "░";
    public static final String PLAYER_CHAR = "♥";
    public static final String WALL_CHAR = "▓";
    public static final String WALL_CHAR_BLUE = "█";

    public static final int INVALID_POSITION = -100;

    public static final String POWERUP = "¤";
    public static final String DOOR = "O";
    public static final String DOOR_HIDDEN = "o";

    public static final int MAX_BOMB_RADIUS = 5;

    public static final int INITIAL_ID = 4000;

    public static String CORNER_CHAR = "▓";

    public static String OBJECT_CONTROL_MENU = ">";
    public static String OBJECT_INPUT_TEXT = "^";
    public static String OBJECT_PLAYER_POINTER = "@";
    public static String OBJECT_BOMB = "δ";
    public static String OBJECT_FLAME = "+";

    public static final int TERMINAL_WIDTH = 120;
    public static final int TERMINAL_HEIGHT = 40;

    public static int PORT = 8080;
    public static int BOMB_DELAY = 3000;
    public static int BOMB_RADIUS = 1;
    public static int FLAME_DELAY = 500;

    public static String PLAYER_COLOR = ConsoleColors.GREEN;
    public static String ENEMY_PLAYER_COLOR = ConsoleColors.RED;

    public static final int POWERUP_ODD = 10;
}
