package org.academiadecodigo.bomberwoman.threads.network;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

import java.util.Map;

/**
 * Created by codecadet on 09/11/2017.
 */
public class ClientEventHandler {

    public static void handleObjectSpawnEvent(String[] eventInfo, Game game) {

        GameObjectType goType = GameObjectType.values()[Integer.parseInt(eventInfo[2])];
        int id = Integer.parseInt(eventInfo[3]);
        int x = Integer.parseInt(eventInfo[4]);
        int y = Integer.parseInt(eventInfo[5]);
        spawnObject(goType, id, x, y, game);
        game.refreshRenderThread();
    }

    private static void spawnObject(GameObjectType goType, int id, int x, int y, Game game) {

        Map<Integer, GameObject> gameObjectMap = game.getGameObjects();
        GameObject gameObject = GameObjectFactory.byType(id, goType, x, y);

        synchronized (gameObjectMap) {

            gameObjectMap.put(id, gameObject);

        }

    }

    public static void handleObjectMoveEvent(String[] eventInfo, Game game) {

        Map<Integer, GameObject> gameObjectMap = game.getGameObjects();

        synchronized (gameObjectMap) {

            GameObject obj = gameObjectMap.get(Integer.parseInt(eventInfo[2]));
            obj.setPosition(Integer.parseInt(eventInfo[3]), Integer.parseInt(eventInfo[4]));
        }

        game.refreshRenderThread();
    }
}
