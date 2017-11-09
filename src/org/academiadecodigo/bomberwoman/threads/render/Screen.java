package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.Level;
import org.academiadecodigo.bomberwoman.levels.LevelFileLocator;
import org.academiadecodigo.bomberwoman.threads.RenderThread;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.Map;

/**
 * Created by miro on 06/11/2017.
 */
public class Screen {

    private final RenderThread renderThread;

    private Level iFile;

    private ScreenFrame screenFrame;

    private Map<Integer, GameObject> gameObjectMap;

    public Screen(RenderThread renderThread) {

        this.renderThread = renderThread;
    }

    public void init(int width, int height) {

        screenFrame = new ScreenFrame(width, height);
    }

    public void update() {

        screenFrame.update();

        for(GameObject letter : iFile.getLetters().values()) {

            putObjectInScreen(letter);
        }

        iFile.update();
    }

    public void draw() {

        System.out.println("\r" + screenFrame.getContent());
    }

    void putObjectInScreen(GameObject gameObject) {

        putStringAt(gameObject.getDrawInfo(), gameObject.getX(), gameObject.getY());
    }

    private void putStringAt(String s, int x, int y) {

        screenFrame.putStringAt(s, x, y);
    }

    private boolean isSplash() {

        return iFile.getLevelFileLocator().isSplash();
    }

    public void changeFrame(LevelFileLocator level, Map<Integer, GameObject> gameObjectMap) {

        if(level == null) {

            level = LevelFileLocator.SPLASH;
        }

        this.gameObjectMap = gameObjectMap;
        iFile = new Level(level, gameObjectMap);
        init(iFile.getWidth(), iFile.getHeight());
        update();
        renderThread.refresh();
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
                changeFrame(chooseMenu(isSplash() ? 0 : choice()), gameObjectMap);
                break;
            case TAB:
                changeFrame(chooseMenu(2), gameObjectMap);
                break;
        }

        renderThread.refresh();
    }

    private int choice() {

        return iFile.choice();
    }

    public LevelFileLocator currentMenu() {

        return iFile.getLevelFileLocator();
    }

    public LevelFileLocator chooseMenu(int choice) {

        return currentMenu().selectLevelOfChoice(choice);
    }

    public boolean isMenu() {

        return iFile.getLevelFileLocator().getFilePath().contains("Menu");
    }
}