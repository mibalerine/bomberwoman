package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by miro on 06/11/2017.
 */
public enum  GameObjectType {

    PLAYER(Constants.PLAYER_CHAR),
    BRICK(Constants.BRICK_CHAR);

    private String drawChar;
    GameObjectType(String drawChar) {

        this.drawChar = drawChar;
    }

    public String getDrawChar() {

        return drawChar;
    }
}