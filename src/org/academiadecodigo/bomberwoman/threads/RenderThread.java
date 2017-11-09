package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.render.Screen;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by codecadet on 06/11/17.
 */
public class RenderThread implements Runnable {

    private Timer timer = new Timer();

    private Screen screen;

    private volatile boolean dirty = true;

    private int timeToUpdate;

    public RenderThread(ScreenHolder startingLevel, int timeToUpdate, Map<Integer, GameObject> gameObjectMap) {

        this.screen = new Screen(this);

        changeScreen(startingLevel, gameObjectMap);

        this.timeToUpdate = timeToUpdate;
    }

    @Override
    public void run() {

        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                screen.update();

                if(dirty) {

                    dirty = false;
                    screen.draw();
                }
            }
        }, 0, timeToUpdate);
    }

    public void keyPressed(Keys key) {

        screen.keyPressed(key);
    }

    public void refresh() {

        screen.update();
        dirty = true;
    }

    public void changeScreen(ScreenHolder level, Map<Integer, GameObject> gameObjectMap) {

        screen.changeFrame(level, gameObjectMap);
    }

    public boolean isDrawingMenu() {

        return screen.isMenu();
    }
}