package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;

/**
 * Created by codecadet on 09/11/17.
 */
public class Player extends GameObject implements Destroyable {

    private int bombRadius = Constants.BOMB_RADIUS;
    private boolean vest;

    public Player(int id, int x, int y, boolean self) {

        super(id, (self ? Constants.PLAYER_COLOR : Constants.ENEMY_PLAYER_COLOR) + GameObjectType.PLAYER.getDrawChar() + ConsoleColors.RESET, x, y);
    }

    public void increaseBombRadius() {

        if(bombRadius + 1 < Constants.MAX_BOMB_RADIUS) {

            bombRadius++;
        }
    }

    public void wearVest() {
        vest = true;
    }

    public int getBombRadius() {

        return bombRadius;
    }

    public boolean wearingVest() {
        return vest;
    }

    public void stripVest() {
        vest = false;
    }
}