package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;

import static org.academiadecodigo.bomberwoman.Constants.NO_LEVEL_CREATED;
import static org.academiadecodigo.bomberwoman.Constants.QUIT_GAME;

/**
 * Created by miro on 08/11/2017.
 */
public enum ScreenHolder {

    SPLASH("/menu/Splash", 1, 1, 1),
    MENU_MAIN("/menu/MenuMain", NO_LEVEL_CREATED, 2, QUIT_GAME),
    MENU_MP_MAIN("/menu/MenuMPMain", 3, 4, MENU_MAIN.ordinal()),
    MENU_MP_HOST("/menu/MenuMPHost", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal()),
    MENU_MP_JOIN("/menu/MenuMPJoin", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal()),
    MENU_MP_WAIT_CLIENT("/menu/MenuMPWaitingClients", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_HOST.ordinal()),
    LEVEL_1("/levels/level1", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED);

    private String filePath;

    private int[] choices;

    ScreenHolder(String fileName, int... choices) {

        this.filePath = fileName + ".txt";

        this.choices = choices;
    }

    public String getFilePath() {

        return filePath;
    }

    public ScreenHolder selectLevelOfChoice(int choice) {

        try {

            if(choices[choice] == Constants.QUIT_GAME) {

                Utils.quitGame();
            }

            //choice 0-1-2
            return values()[choices[choice]];
        }
        catch(ArrayIndexOutOfBoundsException e) {

            return SPLASH;
        }
    }

    public boolean isMenu() {

        return this.equals(SPLASH) || this.filePath.contains("Menu");
    }

    public boolean canHandleNumberInput() {

        return this == MENU_MP_HOST || this == MENU_MP_JOIN;
    }
}