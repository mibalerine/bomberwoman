package org.academiadecodigo.bomberwoman;

/**
 * Created by codecadet on 06/11/17.
 */
public class Position {

    private int x;
    private int y;

    public void setY(int y) {
        this.y = Utils.clamp(y, 0, Game.HEIGHT - 2);
    }

    public void setX(int x) {
        this.x = Utils.clamp(x, 0, Game.WIDTH - 2);
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
}
