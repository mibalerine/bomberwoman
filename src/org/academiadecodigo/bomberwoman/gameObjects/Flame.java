package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;

/**
 * Created by codecadet on 11/11/2017.
 */
public class Flame extends GameObject{

    public Flame(int id, int x, int y) {
        super(id, GameObjectType.FLAME.getDrawChar(), x, y, ConsoleColors.YELLOW);
    }


}
