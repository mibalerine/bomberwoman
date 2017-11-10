package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by miro on 06/11/2017.
 */
public enum GameObjectType {

    PLAYER(Constants.PLAYER_CHAR),
    BRICK(ConsoleColors.RED + Constants.BRICK_CHAR),
    WALL(ConsoleColors.WHITE + Constants.WALL_CHAR),
    EMPTY(ConsoleColors.WHITE + " ");

    private String drawChar;

    GameObjectType(String drawChar) {

        this.drawChar = drawChar + ConsoleColors.RESET;
    }

    public static GameObjectType byChar(String c) {

        for(GameObjectType gameObjectType : values()) {

            if(gameObjectType.drawChar.equals(c)) {

                return gameObjectType;
            }
        }
        return EMPTY;
    }

    public String getDrawChar() {

        return drawChar;
    }
}