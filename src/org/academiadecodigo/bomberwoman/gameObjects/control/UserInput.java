package org.academiadecodigo.bomberwoman.gameObjects.control;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 09/11/2017.
 */
public class UserInput extends GameObject {

    private int originalX;

    private int maxTranslationsUP;

    private int upTransitions;

    public UserInput(int id, int x, int y, int maxTranslationUP) {

        super(id, Constants.OBJECT_INPUT_TEXT, x, y, ConsoleColors.GREEN);

        originalX = x;
        this.maxTranslationsUP = maxTranslationUP;
    }

    @Override
    public void translate(int x, int y) {

        if(upTransitions + x - 1> maxTranslationsUP || upTransitions + x < 0) {

            return;
        }

        upTransitions += x;

        super.translate(x, y);
    }

    public boolean underADot(int increment) {

        int distanceToSource = getX() + increment - originalX;

        return distanceToSource == 3 || distanceToSource == 7 || distanceToSource == 11;
    }

    public boolean canMove() {

        return onLastCell() && upTransitions >= 0;
    }

    private boolean onLastCell() {

        return upTransitions - 1 < maxTranslationsUP;
    }
}