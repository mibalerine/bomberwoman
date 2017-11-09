package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by codecadet on 09/11/17.
 */
public class Brick extends GameObject implements Destroyable {

    public Brick(int id, int x, int y) {
        super(id, Constants.BRICK_CHAR, x, y);
    }

    @Override
    public void destroy() {

    }
}
