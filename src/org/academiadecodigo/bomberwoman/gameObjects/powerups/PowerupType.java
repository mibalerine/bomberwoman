package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.Constants;

public enum PowerupType {

    BOMB_RADIUS_INCREASE(Constants.POWERUP),
    VEST(Constants.POWERUP),
    DOOR("O");

    private final String drawChar;

    PowerupType(String drawChar) {

        this.drawChar = drawChar;
    }

    public String getDrawChar() {

        return drawChar;
    }
}