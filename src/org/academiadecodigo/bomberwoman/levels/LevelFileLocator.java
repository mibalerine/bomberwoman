package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Utils;

/**
 * Created by miro on 08/11/2017.
 */
public enum LevelFileLocator {

    SPLASH("Splash", 1, 1, 1),
    MENU_MAIN("MenuMain", -5, 2, -100),
    MENU_MP_MAIN("MenuMPMain", 3, -5, 1),
    MENU_MP_HOST("MenuMPHost", -5, -5, 2);

    private static final int QUIT_GAME = -100;

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

            if(choices[choice] == QUIT_GAME) {

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