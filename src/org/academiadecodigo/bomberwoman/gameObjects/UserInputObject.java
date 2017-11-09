package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by miro on 09/11/2017.
 */
public class UserInputObject extends GameObject {

    private int originalX;

    private int maxTranslation;

    private int translations;

    public UserInputObject(int id, int x, int y, int maxTranslation) {

        super(id, Constants.OBJECT_INPUT_TEXT, x, y, ConsoleColors.GREEN);

        originalX = x;
        this.maxTranslation = maxTranslation;
    }

    @Override
    public void translate(int x, int y) {

        if(translations + x < 0 || translations + x > maxTranslation) {

            x = 0;
        }

        translations += x;
        super.translate(x, y);
    }
}
