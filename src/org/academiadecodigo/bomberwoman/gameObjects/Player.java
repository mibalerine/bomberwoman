package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;

/**
 * Created by codecadet on 09/11/17.
 */
public class Player extends GameObject implements Destroyable {

    private int bombRadius = Constants.BOMB_RADIUS;

    public Player(int id, int x, int y) {

        super(id, GameObjectType.PLAYER.getDrawChar(), x, y);
    }

    public void increaseBombRadius() {

        bombRadius++;
    }

    public int getBombRadius() {

        return bombRadius;
    }
}