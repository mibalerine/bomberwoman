package org.academiadecodigo.bomberwoman.threads.server;

import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.events.ObjectMoveEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.threads.ServerThread;

/**
 * Created by codecadet on 09/11/2017.
 */
public abstract class ServerEventHandler {

    public static int handleObjectSpawnEvent(String[] eventInfo, Integer id, ServerThread serverThread) {

        if (!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3]) ||
                !Utils.isNumber(eventInfo[4]) || !Utils.isNumber(eventInfo[5])) {

            System.out.println("not a number!");
            return id;
        }

        if (!GameObject.isGameObject(Integer.parseInt(eventInfo[2]))) {
            System.out.println("not a game object!");
            return id;
        }

        int objectTypeNum = Integer.parseInt(eventInfo[2]);
        int x = Integer.parseInt(eventInfo[4]);
        int y = Integer.parseInt(eventInfo[5]);

        GameObjectType goType = GameObjectType.values()[objectTypeNum];
        serverThread.getGameObjectMap().put(id, GameObjectFactory.byType(id, goType, x, y));
        serverThread.broadcast((new ObjectSpawnEvent(goType, id, x, y)).toString());
        return ++id;
    }

    public static void handleObjectMoveEvent(String[] eventInfo, ServerThread serverThread) {

        if (!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3]) ||
                !Utils.isNumber(eventInfo[4])) {

            System.out.println("not a number!");
        }

        int id = Integer.parseInt(eventInfo[2]);
        int x = Integer.parseInt(eventInfo[3]);
        int y = Integer.parseInt(eventInfo[4]);


        GameObject gameObject = serverThread.getGameObjectMap().get(id);
        gameObject.setPosition(x, y);
        serverThread.broadcast(new ObjectMoveEvent(gameObject, Direction.STAY).toString());

    }

}
