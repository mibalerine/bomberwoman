package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;

/**
 * Created by codecadet on 06/11/17.
 */
public class Position {

    private int x;

    private int y;

    public int getY() {

        return y;
    }

    public void setY(int y) {

        this.y = Utils.clamp(y, 0, Game.HEIGHT - 2);
    }

    public int getX() {

        return x;
    }

    public void setX(int x) {

        this.x = Utils.clamp(x, 0, Game.WIDTH - 2);
    }
}