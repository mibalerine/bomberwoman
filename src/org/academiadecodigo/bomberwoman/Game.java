package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.levels.LevelFileLocator;
import org.academiadecodigo.bomberwoman.threads.InputThread;
import org.academiadecodigo.bomberwoman.threads.LogicThread;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;
import org.academiadecodigo.bomberwoman.threads.RenderThread;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class Game {

    public static int WIDTH = 120;

    public static int HEIGHT = 40;

    private final Map<Integer, GameObject> gameObjects;

    private LogicThread logicThread;
    private RenderThread renderThread;
    private NetworkThread networkThread;

    public Game() {

        gameObjects = new HashMap<>();
    }

    void start() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Utils.rawMode();

        //networkThread = new NetworkThread(gameObjects, "localhost");
        //executorService.submit(networkThread);

        renderThread = new RenderThread(LevelFileLocator.SPLASH, 50, gameObjects);
        executorService.submit(renderThread);

        executorService.submit(new InputThread(this));

        logicThread = new LogicThread(networkThread, gameObjects, new Player(-23));
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
                //logicThread.keyPressed(key);
                break;
            case UP:
                renderThread.keyPressed(key);
                //logicThread.keyPressed(key);
                break;
            case TAB:
                renderThread.keyPressed(key);
                break;
            case PLACE_BOMB:
                logicThread.keyPressed(key);
                break;
        }
    }

    public void refreshRenderThread() {
        //renderThread.refresh();
    }
}