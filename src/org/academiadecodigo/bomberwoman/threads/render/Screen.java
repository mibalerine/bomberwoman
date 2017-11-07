package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 06/11/2017.
 */
public class Screen {

    protected ScreenFrame screenFrame;
    boolean splash;

    public Screen(int width, int height) {

        screenFrame = new ScreenFrame(width, height);
    }

    public void update() {

        screenFrame.update();
    }

    public void draw() {

        System.out.println("\r" + screenFrame.getContent());
    }

    public void putObjectInScreen(GameObject gameObject) {

        putStringAt(gameObject.getDrawChar(), gameObject.getX(), gameObject.getY());
    }

    public void putStringAt(String s, int x, int y) {

        screenFrame.putStringAt(s, x, y);
    }

    public int getWidth() {

        return screenFrame.width();
    }

    public int getHeight() {

        return screenFrame.height();
    }

    public boolean isSplash() {

        return splash;
    }
}