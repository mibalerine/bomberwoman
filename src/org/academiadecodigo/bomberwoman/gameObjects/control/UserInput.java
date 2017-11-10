package org.academiadecodigo.bomberwoman.gameObjects.control;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 09/11/2017.
 */
public class UserInput extends GameObject {

    private int originalX, originalY;

    private int endIPX;

    private int maxTranslationsUP;

    private int upTransitions, downTransitions;

    //4 down, 4 right

    public UserInput(int id, int x, int y, int maxTranslationUP) {

        super(id, Constants.OBJECT_INPUT_TEXT, x, y, ConsoleColors.GREEN);

        originalX = x;
        endIPX = originalX + 15;
        originalY = y;
        this.maxTranslationsUP = maxTranslationUP;
    }

    @Override
    public void translate(int x, int y) {

        super.translate(x, y);

        if(!movedDown()) {

            upTransitions += x;

            if(upTransitions - 1 >= maxTranslationsUP) {

                super.setPosition(originalX + 4, originalY + 4);
            }
        }
        else {

            downTransitions += x;

            if(downTransitions <= 0) {

                super.setPosition(endIPX, originalY);
            }
        }
    }

    private boolean movedDown() {

        return getY() == originalY + 4;
    }

    public boolean underADot(int increment) {

        int distanceToSource = getX() + increment - originalX;
        if(getY() != originalY) {

            return false;
        }

        return distanceToSource == 3 || distanceToSource == 7 || distanceToSource == 11;
    }

    public boolean canMove() {

        return downTransitions < 5;
    }
}