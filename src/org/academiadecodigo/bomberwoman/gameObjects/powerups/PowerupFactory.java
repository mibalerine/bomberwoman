package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

import java.util.Random;

/**
 * Created by miro on 12/11/2017.
 */
public class PowerupFactory {

    public static GameObject random(int id, int x, int y) {

        int r = new Random().nextInt(PowerupType.values().length);
        switch(PowerupType.values()[r]) {

            case VEST:
                return new PowerupVest(id, x, y);

            case BOMB_RADIUS_INCREASE:
            default:
                return new PowerupBombRadius(id, x, y);
        }
    }
}