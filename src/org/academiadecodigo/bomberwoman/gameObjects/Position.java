package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;

/**
 * Created by codecadet on 06/11/17.
 */
public class Position {

    private int x;

    private int y;

    int getY() {

        return y;
    }

    public void setY(int y) {

        this.y = Utils.clamp(y, 1, Game.HEIGHT - 2);
    }

    int getX() {

        return x;
    }

    public void setX(int x) {

        this.x = Utils.clamp(x, 1, Game.WIDTH - 2);
    }

    public void translate(int x, int y) {

        setPos(getX() + x, getY() + y);
    }

    public void setPos(int x, int y) {

        setX(x);
        setY(y);
    }
}