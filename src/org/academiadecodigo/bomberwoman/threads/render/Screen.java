package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.Level;
import org.academiadecodigo.bomberwoman.levels.LevelFileLocator;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

/**
 * Created by miro on 06/11/2017.
 */
public class Screen {

    private Level iFile;

    private ScreenFrame screenFrame;

    public void init(int width, int height) {

        screenFrame = new ScreenFrame(width, height);
    }

    public void update() {

        screenFrame.update();

        for(GameObject letter : iFile.getLetters()) {

            putObjectInScreen(letter);
        }

        iFile.update();
    }

    public void draw() {

        System.out.println("\r" + screenFrame.getContent());
    }

    void putObjectInScreen(GameObject gameObject) {

        putStringAt(gameObject.getDrawChar(), gameObject.getX(), gameObject.getY());
    }

    private void putStringAt(String s, int x, int y) {

        screenFrame.putStringAt(s, x, y);
    }

    /*public int getWidth() {

        return screenFrame.width();
    }

    public int getHeight() {

        return screenFrame.height();
    }
    */
    private boolean isSplash() {

        return iFile.getLevelFileLocator().isSplash();
    }

    public void changeFrame(LevelFileLocator level) {

        if(level == null) {

            level = LevelFileLocator.SPLASH;
        }

        iFile = new Level(level);
        init(iFile.getWidth(), iFile.getHeight());
    }

    public void keyPressed(Keys key) {

        switch(key) {
            case UP:
                iFile.moveSelectionBy(-2);
                break;
            case DOWN:
                iFile.moveSelectionBy(2);
                break;
            case ENTER:
                changeFrame(chooseMenu(isSplash() ? 0 : choice()));
                break;
        }
    }

    private int choice() {

        return iFile.choice();
    }

    public LevelFileLocator previousMenu() {

        return chooseMenu(2);
    }

    public LevelFileLocator currentMenu() {

        return iFile.getLevelFileLocator();
    }

    public LevelFileLocator chooseMenu(int choice) {

        return currentMenu().selectLevelOfChoice(choice);
    }
}