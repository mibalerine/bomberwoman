package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.events.PowerUpPickupEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;

/**
 * Created by miro on 12/11/2017.
 */
public abstract class Powerup extends GameObject implements Destroyable {

    private PowerupType powerupType;

    public Powerup(PowerupType powerupType, int id, int x, int y, String color) {

        super(id, powerupType.getDrawChar(), x, y, color);
        this.powerupType = powerupType;
    }

    public void pickup(int playerId, NetworkThread networkThread) {

        networkThread.sendEvent(new PowerUpPickupEvent(playerId, getId()));
    }

    public PowerupType getPowerupType() {

        return powerupType;
    }
}