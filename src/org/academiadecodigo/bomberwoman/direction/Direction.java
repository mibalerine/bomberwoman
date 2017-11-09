package org.academiadecodigo.bomberwoman.direction;

/**
 * Created by codecadet on 09/11/17.
 */
public enum Direction {
    UP(0, -1),
    RIGHT(1, 0),
    DOWN(0, 1),
    LEFT(-1, 0),
    STAY(0, 0);

    private int vertical;
    private int horizontal;

    Direction(int horizontal, int vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }
}
