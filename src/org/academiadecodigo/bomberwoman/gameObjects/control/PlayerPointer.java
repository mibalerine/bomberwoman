package org.academiadecodigo.bomberwoman.gameObjects.control;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 10/11/2017.
 */
public class PlayerPointer extends GameObject {

    public PlayerPointer(int id, int x, int y) {

        super(id, Constants.OBJECT_PLAYER_POINTER, x, y, ConsoleColors.BLUE);
    }
}
