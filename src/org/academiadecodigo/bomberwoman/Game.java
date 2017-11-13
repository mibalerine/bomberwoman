package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.events.PlayerQuitEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.*;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by codecadet on 06/11/17.
 */
public class Game {

    public static int WIDTH = Constants.TERMINAL_WIDTH;

    public static int HEIGHT = Constants.TERMINAL_HEIGHT;

    private static Game instance;

    private final Map<Integer, GameObject> gameObjects;

    private LogicThread logicThread;

    private RenderThread renderThread;

    private NetworkThread networkThread;

    private ServerThread serverThread;

    private ExecutorService executorService;

    private Future<?> networkThreadCancel;

    private int playerId;

    private Game() {

        gameObjects = new HashMap<>();
    }

    public static Game getInstance() {

        if(instance == null) {

            instance = new Game();
            instance.executorService = Executors.newFixedThreadPool(4);
        }

        return instance;
    }

    void start() {

        executorService = Executors.newFixedThreadPool(4);

        networkThread = new NetworkThread("localhost", this);

        Utils.rawMode();

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

        playerId = id;
        logicThread.setPlayerId(id);
    }

    public int getPlayerId() {
        return playerId;
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

        networkThreadCancel = executorService.submit(networkThread);
    }

    public ServerThread getServerThread() {

        return serverThread;
    }

    public void changeScreen(ScreenHolder screenHolder) {

        renderThread.changeScreen(screenHolder, gameObjects);
    }

    public void clearScreen() {

        synchronized (gameObjects) {

            gameObjects.clear();
        }
    }

    public void closeClient() {

        Game.getInstance().changeScreen(ScreenHolder.SPLASH);
        Game.getInstance().changeScreen(ScreenHolder.MENU_MAIN);

        networkThread.sendMessage((new PlayerQuitEvent(playerId)).toString());

        if (serverThread != null) {
            serverThread.closeServer();
        }

    }
}