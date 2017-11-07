package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.levels.LevelLoader;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.render.MenuScreen;
import org.academiadecodigo.bomberwoman.threads.render.Screen;

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

    public void setScreen(Screen screen) {

        this.screen = screen;
        Game.WIDTH = screen.getWidth();
        Game.HEIGHT = screen.getHeight();
    }

    public void enterPressed() {

        if(screen.isSplash()) {

            setScreen(new MenuScreen("/menu/MainMenu.txt", false));
        }
    }
}