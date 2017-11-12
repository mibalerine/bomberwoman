package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.levels.Level;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.Map;

/**
 * Created by miro on 06/11/2017.
 */
public class Screen {

    private Level level;

    private ScreenFrame screenFrame;

    private Map<Integer, GameObject> gameObjectMap;

    public void init(int width, int height) {

        screenFrame = new ScreenFrame(width, height);
    }

    public void update() {

        screenFrame.update();

        synchronized(level.getLetters()) {

            for(GameObject letter : level.getLetters().values()) {

                putObjectInScreen(letter);
            }
        }

        level.update();
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

    public boolean isMenu() {

        return level.getScreenHolder().isMenu();
    }

    public void changeFrame(ScreenHolder level, Map<Integer, GameObject> gameObjectMap) {

        if(level == null) {

            level = ScreenHolder.SPLASH;
        }

        this.gameObjectMap = gameObjectMap;
        this.level = new Level(level, gameObjectMap);
        init(this.level.getWidth(), this.level.getHeight());

        Game.WIDTH = this.level.getWidth();
        Game.HEIGHT = this.level.getHeight();

        update();
        Game.getInstance().refreshRenderThread();
    }

    public void keyPressed(Keys key) {

        if(level.getScreenHolder().canHandleNumberInput()) {

            if(Keys.isNumber(key) || key == Keys.BACKSPACE) {

                if(key == Keys.BACKSPACE) {

                    level.erase();
                }
                else {

                    level.inputNumber(Integer.parseInt(key.toString().replaceAll("NUM_", "")));
                }
            }
            else if(key == Keys.ENTER) {

                level.pressedEnter(this, gameObjectMap);
                return;
            }
        }

        if(level.getScreenHolder() == ScreenHolder.MENU_MP_WAIT_CLIENT) {

            level.pressedKeyOnWaitClient(key, gameObjectMap.values());
        }
        else {

            switch(key) {
                case UP:
                    level.moveSelectionBy(-2);
                    break;
                case DOWN:
                    level.moveSelectionBy(2);
                    break;
                case ENTER:

                    ScreenHolder nextScreen = chooseMenu(level.choice());

                    if(nextScreen == ScreenHolder.LEVEL_0) {

                        Utils.hostAndConnect(1);
                    }
                    else {

                        changeFrame(nextScreen, gameObjectMap);
                    }
                    break;
                case TAB:
                    changeFrame(chooseMenu(2), gameObjectMap);
                    break;
            }
        }

        Game.getInstance().refreshRenderThread();
    }

    private ScreenHolder chooseMenu(int choice) {

        return level.getScreenHolder().selectLevelOfChoice(choice);
    }
}