package org.academiadecodigo.bomberwoman.threads.logic;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.gameObjects.powerups.Powerup;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;

/**
 * Created by miro on 07/11/2017.
 */
public class CollisionDetector {

    public static boolean canMove(int x, int y, int playerId, NetworkThread networkThread) {

        if (!(x > 0) || !(x < Game.WIDTH) || !(y > 0) || !(y < Game.HEIGHT - 1)) {
            return false;
        }

        GameObject gameObject = Utils.getObjectAt(Game.getInstance().getGameObjects().values(), x, y);

        if(gameObject instanceof Powerup) {

            ((Powerup) gameObject).pickup(playerId, networkThread);
        }

        return (gameObject == null || gameObject instanceof Player || gameObject instanceof Powerup);
    }
}