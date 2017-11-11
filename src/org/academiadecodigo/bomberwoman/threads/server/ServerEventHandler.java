package org.academiadecodigo.bomberwoman.threads.server;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectMoveEvent;
import org.academiadecodigo.bomberwoman.gameObjects.Bomb;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.threads.ServerThread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by codecadet on 09/11/2017.
 */
public abstract class ServerEventHandler {

    public static int handleObjectSpawnEvent(String[] eventInfo, Integer id, ServerThread serverThread) {

        if(!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3]) || !Utils.isNumber(eventInfo[4]) || !Utils.isNumber(eventInfo[5])) {

            System.out.println("not a number!");
            return id;
        }

        if(!GameObject.isGameObject(Integer.parseInt(eventInfo[2]))) {
            System.out.println("not a game object!");
            return id;
        }

        GameObjectType gameObjectType = GameObjectType.values()[Integer.parseInt(eventInfo[2])];
        int x = Integer.parseInt(eventInfo[4]);
        int y = Integer.parseInt(eventInfo[5]);

        GameObject gameObject = serverThread.spawnObject(gameObjectType, id, x, y, true);

        if(gameObjectType == GameObjectType.BOMB) {

            setBombExplode((Bomb) gameObject);
        }

        return ++id;
    }

    public static void handleObjectMoveEvent(String[] eventInfo, ServerThread serverThread) {

        if(!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3]) || !Utils.isNumber(eventInfo[4])) {

            System.out.println("not a number!");
            return;
        }

        int id = Integer.parseInt(eventInfo[2]);
        int x = Integer.parseInt(eventInfo[3]);
        int y = Integer.parseInt(eventInfo[4]);

        GameObject gameObject = serverThread.getGameObjectMap().get(id);

        if(gameObject == null) {
            return;
        }

        gameObject.setPosition(x, y);
        serverThread.broadcast(new ObjectMoveEvent(gameObject, Direction.STAY).toString());
    }

    public static void setBombExplode(final Bomb bomb) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if(Game.getInstance().getServerThread().getGameObjectMap().get(bomb.getId()) != null) {

                    bomb.explode();
                }
            }
        }, Constants.BOMB_DELAY);
    }

}