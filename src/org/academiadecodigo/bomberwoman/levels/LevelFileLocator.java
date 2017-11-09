package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import static org.academiadecodigo.bomberwoman.Constants.*;

/**
 * Created by miro on 08/11/2017.
 */
public enum LevelFileLocator {

    SPLASH("Splash", 1, 1, 1),
    MENU_MAIN("MenuMain", NO_LEVEL_CREATED, 2, QUIT_GAME),
    MENU_MP_MAIN("MenuMPMain", 3, NO_LEVEL_CREATED, MENU_MAIN.ordinal()),
    MENU_MP_HOST("MenuMPHost", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal()),
    MENU_MP_JOIN("MenuMPJoin", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal());


    private String filePath;

    LevelFileLocator(String fileName, int... choices) {

        this.filePath = "/menu/" + fileName + ".txt";

        this.choices = choices;
    }

    private int[] choices;

    public String getFilePath() {

        return filePath;
    }

    public boolean isSplash() {

        return this.equals(SPLASH);
    }

    public LevelFileLocator selectLevelOfChoice(int choice) {

        try {

            if(choices[choice] == Constants.QUIT_GAME) {

                Utils.quitGame();
            }

            //choice 0-1-2
            return values()[choices[choice]];
        }
        catch(ArrayIndexOutOfBoundsException e) {

            return null;
        }
    }
}