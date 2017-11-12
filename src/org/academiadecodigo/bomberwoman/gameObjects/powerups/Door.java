package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.ConsoleColors;

/**
 * Created by miro on 12/11/2017.
 */
public class Door extends Powerup {

    public Door(int id, int x, int y) {

        super(PowerupType.DOOR, id, x, y, ConsoleColors.YELLOW_BOLD_BRIGHT);
    }
}