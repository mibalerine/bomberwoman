package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;

/**
 * Created by miro on 12/11/2017.
 */
public abstract class Powerup extends GameObject implements Destroyable {

    private PowerupType powerupType;

    public Powerup(PowerupType powerupType, int id, int x, int y, String color) {

        super(id, GameObjectType.POWER_UP.getDrawChar(), x, y, color);
        this.powerupType = powerupType;
    }

    public abstract void pickup(int playerId, NetworkThread networkThread);

    public PowerupType getPowerupType() {

        return powerupType;
    }
}