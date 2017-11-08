package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.threads.InputThread;
import org.academiadecodigo.bomberwoman.threads.LogicThread;
import org.academiadecodigo.bomberwoman.threads.NetworkThread;
import org.academiadecodigo.bomberwoman.threads.RenderThread;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.render.MenuScreen;

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

        int timeToDraw = 100;
        Utils.rawMode();

        //TODO WHILE CREATING, REMOVE THIS
        //networkThread = new NetworkThread(gameObjects, "192.168.0.25");
        //executorService.submit(networkThread);

        renderThread = new RenderThread(WIDTH, HEIGHT, timeToDraw);
        executorService.submit(renderThread);

        executorService.submit(new InputThread(this));

        logicThread = new LogicThread();
        executorService.submit(logicThread);

        renderThread.setScreen(new MenuScreen("/menu/Splash.txt", true));
    }

    public void keyPressed(Keys key) {

        switch(key) {

            case QUIT_GAME:
                quit();
                break;
            case ENTER:
                renderThread.enterPressed(key);
                break;
            case UP:
                renderThread.directionKeyPressed(key);
                break;
            case DOWN:
                renderThread.directionKeyPressed(key);
                break;
            case RIGHT:
            case LEFT:
            case PLACE_BOMB:
                networkThread.sendMessage(new ObjectSpawnEvent(GameObjectType.PLAYER, 10, 10).toString());
                logicThread.keyPressed(key);
                break;
        }
    }

    private void quit() {

        Utils.quitGame();
    }
}