package org.academiadecodigo.bomberwoman.gameObjects.control;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 07/11/2017.
 */
public class MenuSelect extends GameObject {

    private int originalY;

    public MenuSelect(int id, int x, int y) {

        super(id, Constants.OBJECT_CONTROL_MENU, x, y);

        originalY = y;
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

    @Override
    public void translate(int x, int y) {


        if(getY() + y < originalY) {

            y = 4;
        }

        if(getY() + y > originalY + 4) {

            y = -4;
        }

        super.translate(x, y);
    }
}