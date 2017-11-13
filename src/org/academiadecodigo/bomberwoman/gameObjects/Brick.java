package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;

/**
 * Created by codecadet on 09/11/17.
 */
public class Brick extends GameObject implements Destroyable {

    public Brick(int id, int x, int y) {

        super(id, GameObjectType.BRICK.getDrawChar(), x, y, ConsoleColors.RED);
    }

}