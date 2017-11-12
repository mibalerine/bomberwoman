package org.academiadecodigo.bomberwoman.threads.network;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;

import java.util.Map;

/**
 * Created by codecadet on 09/11/2017.
 */
public class ClientEventHandler {

    public static void handleLevelStartEvent() {
        Game.getInstance().changeScreen(ScreenHolder.LEVEL_0);
        Game.getInstance().clearScreen();
    }

    public static void handleObjectSpawnEvent(String[] eventInfo, Game game) {

        try {

            GameObjectType goType = GameObjectType.values()[Integer.parseInt(eventInfo[2])];
            int id = Integer.parseInt(eventInfo[3]);
            int x = Integer.parseInt(eventInfo[4]);
            int y = Integer.parseInt(eventInfo[5]);

            boolean shouldRefresh = Boolean.valueOf(eventInfo[6]);

            spawnObject(goType, id, x, y, game);

            if (goType == GameObjectType.PLAYER) {
                //System.out.println("x = " + x);
                //System.out.println("y = " + y);
            }

            if (!shouldRefresh) {

                return;
            }

            game.refreshRenderThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void handleObjectDestroyEvent(String[] eventInfo, Game game) {

        Map<Integer, GameObject> gameObjectMap = game.getGameObjects();

        synchronized (gameObjectMap) {

            gameObjectMap.remove(Integer.parseInt(eventInfo[2]));
        }

        game.refreshRenderThread();
    }

    public static void handlePlayerAssignEvent(String[] eventInfo, Game game) {

        game.setPlayerId(Integer.parseInt(eventInfo[2]));
    }
}
