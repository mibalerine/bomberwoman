package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by codecadet on 09/11/17.
 */
public class Wall extends GameObject {
    public Wall(int id, int x, int y, boolean blue) {
        super(id, (blue ? Constants.WALL_CHAR : GameObjectType.WALL.getDrawChar()), x, y, (blue ? ConsoleColors.BLUE : ConsoleColors.WHITE));
    }
}
