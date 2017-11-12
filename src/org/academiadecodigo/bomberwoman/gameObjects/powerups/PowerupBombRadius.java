package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.events.PowerUpPickupEvent;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;

/**
 * Created by miro on 12/11/2017.
 */
public class PowerupBombRadius extends Powerup {

    public PowerupBombRadius(int id, int x, int y) {

        super(PowerupType.BOMB_RADIUS_INCREASE, id, x, y, ConsoleColors.PURPLE_BOLD_BRIGHT);
    }

    @Override
    public void pickup(int playerId, NetworkThread networkThread) {

        networkThread.sendEvent(new PowerUpPickupEvent(playerId, getId()));
    }
}