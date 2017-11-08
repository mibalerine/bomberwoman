package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.levels.LevelFileLocator;
import org.academiadecodigo.bomberwoman.threads.InputThread;
import org.academiadecodigo.bomberwoman.threads.LogicThread;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;
import org.academiadecodigo.bomberwoman.threads.RenderThread;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class Game {

    public static int WIDTH = 120;

    public static int HEIGHT = 40;

    private final Vector<GameObject> gameObjects = new Vector<>();

    private LogicThread logicThread;
    private RenderThread renderThread;
    private NetworkThread networkThread;

    void start() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int timeToDraw = 80;
        Utils.rawMode();

        //TODO WHILE CREATING, REMOVE THIS
        //networkThread = new NetworkThread(gameObjects, "192.168.0.25");
        //executorService.submit(networkThread);

        renderThread = new RenderThread(LevelFileLocator.SPLASH, timeToDraw);
        executorService.submit(renderThread);

        executorService.submit(new InputThread(this));

        logicThread = new LogicThread();
        executorService.submit(logicThread);
    }

    public void keyPressed(Keys key) {

        switch(key) {

            case QUIT_GAME:
                Utils.quitGame();
                break;
            case ENTER:
                renderThread.keyPressed(key);
                break;
            case DOWN:
                renderThread.keyPressed(key);
                break;
            case UP:
                renderThread.keyPressed(key);
                break;
            case PLACE_BOMB:
                networkThread.sendMessage(new ObjectSpawnEvent(GameObjectType.PLAYER, 10, 10).toString());
                logicThread.keyPressed(key);
                break;
        }
    }
}