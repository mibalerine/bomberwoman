package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

/**
 * Created by codecadet on 06/11/17.
 */
public class LogicThread implements Runnable {

    @Override
    public void run() {

    }

    public void keyPressed(Keys keyPressed) {

        switch(keyPressed) {

            case UP:
                break;
            case DOWN:
                break;
            case RIGHT:
                break;
            case LEFT:
                break;
            case PLACE_BOMB:
                new ObjectSpawnEvent(GameObjectType.PLAYER, 10, 10);
                break;
            case ENTER:
                break;
        }
    }
}