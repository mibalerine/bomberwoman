package org.academiadecodigo.bomberwoman.threads.server;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectMoveEvent;
import org.academiadecodigo.bomberwoman.events.RefreshScreenEvent;
import org.academiadecodigo.bomberwoman.gameObjects.Bomb;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.gameObjects.powerups.Powerup;
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

        boolean isBomb = gameObjectType == GameObjectType.BOMB;

        if(!isBomb) {

            serverThread.spawnObject(gameObjectType, id, x, y, true);
        }
        else {

            GameObject player = Utils.getObjectAt(serverThread.getGameObjectMap().values(), x, y);
            if(player == null || !(player instanceof Player)) {

                return id;
            }

            GameObject bomb = serverThread.spawnBomb(id, (Player) player);
            setDestroyTimer(bomb, Constants.BOMB_DELAY);
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

    public static void setDestroyTimer(final GameObject object, int delay) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                if(Game.getInstance().getServerThread().getGameObjectMap().get(object.getId()) != null) {

                    if(object instanceof Bomb) {
                        ((Bomb) object).explode();
                    }

                    else {
                        Game.getInstance().getServerThread().removeObject(object.getId());
                    }
                }
            }
        }, delay);
    }

    public static void handlePickupPowerupEvent(String[] eventInfo, ServerThread serverThread) {

        if(!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3])) {

            System.out.println("not a number!");
            return;
        }

        int powerUpId = Integer.parseInt(eventInfo[2]);
        GameObject powerup = serverThread.getGameObjectMap().get(powerUpId);

        if(powerup == null || !(powerup instanceof Powerup)) {

            //Huh? Should not happen...
            return;
        }

        int playerId = Integer.parseInt(eventInfo[3]);
        GameObject player = serverThread.getGameObjectMap().get(playerId);

        if(player == null || !(player instanceof Player)) {

            return;
        }

        switch(((Powerup) powerup).getPowerupType()) {

            case BOMB_RADIUS_INCREASE:
                ((Player) player).increaseBombRadius();
                break;
            case DOOR:
                serverThread.loadNextLevel();

            case VEST:
                ((Player) player).wearVest();
                break;
        }

        serverThread.removeObject(powerUpId);
    }
}