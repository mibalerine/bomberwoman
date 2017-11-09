package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by miro on 07/11/2017.
 */
public class MenuSelect extends GameObject {

    private int originalY;

    public MenuSelect(int id, int x, int y) {

        super(id, Constants.OBJECT_CONTROL_MENU, x, y);

        originalY = y;
    }

    public void update() {

        if(getY() < originalY) {

            setPosition(getX(), originalY + 4);
        }

        if(getY() > originalY + 4) {

            setPosition(getX(), originalY);
        }
    }

    public int choice() {

        if(getY() == originalY) {

            return 0;
        }
        else if(getY() == originalY + 2) {

            return 1;
        }

        return 2;
    }

    public int getOriginalY() {

        return originalY;
    }
}