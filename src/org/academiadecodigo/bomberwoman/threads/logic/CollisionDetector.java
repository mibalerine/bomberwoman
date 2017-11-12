package org.academiadecodigo.bomberwoman.threads.logic;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.levels.SpecialObjectHolder;

/**
 * Created by miro on 07/11/2017.
 */
public class CollisionDetector {

    public static boolean canMove(int x, int y) {

        if (!(x > 0) || !(x < Game.WIDTH) || !(y > 0) || !(y < Game.HEIGHT - 1)) {
            return false;
        }

        GameObject gameObject = Utils.getObjectAt(Game.getInstance().getGameObjects().values(), x, y);

        return (gameObject == null || gameObject instanceof Player);
    }

}