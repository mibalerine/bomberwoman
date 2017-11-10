package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
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

        renderThread = new RenderThread(ScreenHolder.MENU_MP_JOIN, 50, gameObjects);
        executorService.submit(renderThread);

        executorService.submit(new InputThread(this));

        logicThread = new LogicThread(networkThread, gameObjects, new Player(-23));
        executorService.submit(logicThread);
    }

    public void keyPressed(Keys key) {

        if(key == Keys.QUIT_GAME) {

            Utils.quitGame();
            return;
        }

        if(renderThread.isDrawingMenu()) {

            renderThread.keyPressed(key);
            return;
        }

        switch(key) {

            case DOWN:
                logicThread.keyPressed(key);
                break;
            case UP:
                logicThread.keyPressed(key);
                break;
            case PLACE_BOMB:
                logicThread.keyPressed(key);
                break;
        }
    }

    public void refreshRenderThread() {

        renderThread.refresh();
    }
}