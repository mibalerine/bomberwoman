package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.LevelFileLocator;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.render.Screen;

import java.util.Map;

/**
 * Created by codecadet on 06/11/17.
 */
public class RenderThread implements Runnable {

    private final int timeToDraw;

    private Screen screen;

    public RenderThread(LevelFileLocator startingLevel, int timeToDraw, Map<Integer, GameObject> gameObjectMap) {

        this.screen = new Screen();
        screen.changeFrame(startingLevel, gameObjectMap);
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

    public void keyPressed(Keys key) {

        screen.keyPressed(key);
    }
}