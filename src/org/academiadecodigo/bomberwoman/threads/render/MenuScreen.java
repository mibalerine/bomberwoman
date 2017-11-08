package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.MenuSelect;
import org.academiadecodigo.bomberwoman.levels.LevelLoader;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

/**
 * Created by miro on 07/11/2017.
 */
public class MenuScreen extends Screen {

    private LevelLoader iFile;

    private int originalY = 0;

    private MenuSelect menuSelect;

    public MenuScreen(String path, boolean splash) {

        this(new LevelLoader(path));

        this.splash = splash;
    }

    private MenuScreen(LevelLoader iFile) {

        super(iFile.getWidth(), iFile.getHeight());

        this.iFile = iFile;

        menuSelect = iFile.getMenuSelect();

        originalY = menuSelect.getY();
    }

    @Override
    public void update() {

        super.update();

        for(GameObject go : iFile.getLetters()) {

            putObjectInScreen(go);
        }
    }

    public void keyPressed(Keys key) {

        if(menuSelect == null) {

            return;
        }

        if(key == Keys.UP) {

            menuSelect.translate(0, -2);
        }
        else if(key == Keys.DOWN) {

            menuSelect.translate(0, 2);
        }
        else if(key == Keys.ENTER) {

        }

        keepSelectionInBounds();
    }

    private void keepSelectionInBounds() {

        //TODO UGH..THIS IS HARDCODED
        if(menuSelect.getY() < originalY) {

            menuSelect.setPosition(menuSelect.getX(), originalY + 4);
        }

        if(menuSelect.getY() > originalY + 4) {

            menuSelect.setPosition(menuSelect.getX(), originalY);
        }
    }
}