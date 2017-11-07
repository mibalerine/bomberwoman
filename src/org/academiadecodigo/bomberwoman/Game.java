package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.threads.InputThread;
import org.academiadecodigo.bomberwoman.threads.LogicThread;
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

    public static int HEIGHT = 30;

    private final Vector<GameObject> gameObjects = new Vector<>();

    private LogicThread logicThread;
    private RenderThread renderThread;

    void start() {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        int timeToDraw = 100;
        Utils.rawMode();

        renderThread = new RenderThread(WIDTH, HEIGHT, timeToDraw);
        executorService.submit(renderThread);

        //executorService.submit(new NetworkThread(gameObjects));
        executorService.submit(new InputThread(this));

        logicThread = new LogicThread();
        executorService.submit(logicThread);

        //renderThread.setScreen(new MenuScreen("/menu/Splash.txt", true));
    }

    public void keyPressed(Keys key) {

        switch(key) {

            case QUIT_GAME:
                quit();
                break;
            case ENTER:
                renderThread.enterPressed();
                break;
            case UP:
                break;
            case DOWN:
                break;
            case RIGHT:
            case LEFT:
            case PLACE_BOMB:
                logicThread.keyPressed(key);
                break;
        }
    }

    private void quit() {

        Utils.bufferedMode();
        Utils.clearScreen();
        System.exit(0);
    }
}