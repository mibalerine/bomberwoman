package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.*;
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

    private static Game instance;

    private final Map<Integer, GameObject> gameObjects;

    private LogicThread logicThread;

    private RenderThread renderThread;

    private NetworkThread networkThread;

    private ServerThread serverThread;

    private ExecutorService executorService;

    private Game() {

        gameObjects = new HashMap<>();
    }

    public static Game getInstance() {

        if(instance == null) {

            instance = new Game();
        }

        return instance;
    }

    void start() {

        executorService = Executors.newFixedThreadPool(4);

        networkThread = new NetworkThread("localhost", this);

        Utils.rawMode();

        networkThread = new NetworkThread("localhost", this);

        renderThread = new RenderThread(ScreenHolder.SPLASH, 50, gameObjects);

        logicThread = new LogicThread(gameObjects);
        logicThread.setNetworkThread(networkThread);

        executorService.submit(logicThread);
        //executorService.submit(networkThread);
        executorService.submit(renderThread);
        executorService.submit(new InputThread(this));

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

        logicThread.keyPressed(key);

    }

    public void refreshRenderThread() {

        if(renderThread == null) {

            return;
        }
        renderThread.refresh();
    }

    public void setPlayerId(int id) {

        logicThread.setPlayerId(id);
    }

    public Map<Integer, GameObject> getGameObjects() {

        return gameObjects;
    }

    public void submitTask(Runnable thread) {

        if(thread instanceof ServerThread) {

            serverThread = (ServerThread) thread;
        }

        executorService.submit(thread);
    }

    public void connectTo(String ipAddress) {

        networkThread.setIpAddress(ipAddress);

        executorService.submit(networkThread);
    }

    public ServerThread getServerThread() {

        return serverThread;
    }
}