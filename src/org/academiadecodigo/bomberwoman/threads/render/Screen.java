package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 06/11/2017.
 */
public class Screen {

    private ScreenFrame screenFrame;

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

        //TODO WAITING FOR CODE
        //screenFrame.putStringAt(gameObject.getDrawChar(), gameObject.getX(), gameObject.getY());
    }
}