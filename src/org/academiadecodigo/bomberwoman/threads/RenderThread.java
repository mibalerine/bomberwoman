package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.threads.render.Screen;

import java.util.List;
import java.util.Vector;

/**
 * Created by codecadet on 06/11/17.
 */
public class RenderThread implements Runnable {

    private final int timeToDraw;

    private Screen screen;

    public RenderThread(int width, int height, int timeToDraw) {

        this.screen = new Screen(width, height);
        this.timeToDraw = timeToDraw;
    }

    @Override
    public void run() {

        long lastCheck = System.currentTimeMillis();
        while(true) {

            if(System.currentTimeMillis() - lastCheck < timeToDraw) {

                continue;
            }

            screen.update();

            lastCheck = System.currentTimeMillis();

            screen.draw();
        }
    }
}