package org.academiadecodigo.bomberwoman;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class Game {

    public static final int WIDTH = 120;
    public static final int HEIGHT = 30;

    private final Vector<GameObject> gameObjects = new Vector<>();

    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        executorService.submit(new RenderThread());
        executorService.submit(new NetworkThread());
        executorService.submit(new InputThread());
        executorService.submit(new LogicThread());
    }
}
