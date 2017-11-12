package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;

/**
 * Created by codecadet on 09/11/17.
 */
public class Player extends GameObject implements Destroyable {

    private int bombRadius = Constants.BOMB_RADIUS;

    public Player(int id, int x, int y, boolean self) {
        super(id, (self ? Constants.PLAYER_COLOR : Constants.ENEMY_COLOR) + GameObjectType.PLAYER.getDrawChar() + ConsoleColors.RESET, x, y);

        System.out.println("y = " + y);
    }

    public void increaseBombRadius() {

        bombRadius++;
    }

    public int getBombRadius() {

        return bombRadius;
    }
}