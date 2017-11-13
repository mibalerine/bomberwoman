package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by miro on 06/11/2017.
 */
public enum GameObjectType {

    PLAYER(Constants.PLAYER_CHAR),
    BRICK(Constants.BRICK_CHAR),
    WALL(Constants.WALL_CHAR),
    BLUE_WALL(Constants.WALL_CHAR_BLUE),
    BOMB(Constants.OBJECT_BOMB),
    FLAME(Constants.OBJECT_FLAME),
    POWER_UP(Constants.POWERUP),
    POWER_UP_DOOR(Constants.DOOR),
    POWER_UP_DOOR_HIDDEN(Constants.DOOR_HIDDEN),
    EMPTY(" ");

    private String drawChar;

    GameObjectType(String drawChar) {

        this.drawChar = drawChar;
    }

    public static GameObjectType byChar(String c) {

        for (GameObjectType gameObjectType : values()) {

            if (gameObjectType.drawChar.equals(c)) {

                return gameObjectType;
            }
        }
        return EMPTY;
    }

    public String getDrawChar() {

        return drawChar;
    }
}