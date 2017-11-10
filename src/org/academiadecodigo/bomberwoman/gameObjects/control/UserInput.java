package org.academiadecodigo.bomberwoman.gameObjects.control;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 09/11/2017.
 */
public class UserInput extends GameObject {

    private int originalX;

    private int maxTranslation;

    private int translations;

    public UserInput(int id, int x, int y, int maxTranslation) {

        super(id, Constants.OBJECT_INPUT_TEXT, x, y, ConsoleColors.GREEN);

        originalX = x;
        this.maxTranslation = maxTranslation;
    }

    @Override
    public void translate(int x, int y) {

        if(translations + x < 0 || translations + x > maxTranslation + 1) {

            x = 0;
        }

        translations += x;
        super.translate(x, y);
    }

    public int getMaxTranslation() {

        return maxTranslation;
    }
}
