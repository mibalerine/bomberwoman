package org.academiadecodigo.bomberwoman.gameObjects.powerups;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.events.PowerUpPickupEvent;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;

/**
 * Created by codecadet on 12/11/17.
 */
public class PowerupVest extends Powerup {

    public PowerupVest(int id, int x, int y) {
        super(PowerupType.VEST, id, x, y, ConsoleColors.CYAN_BOLD_BRIGHT);
    }

    @Override
    public void pickup(int playerId, NetworkThread networkThread) {

        networkThread.sendEvent(new PowerUpPickupEvent(playerId, getId()));
    }
}
