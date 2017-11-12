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
    MENU_MAIN("/menu/MenuMain", 6, 2, QUIT_GAME),
    MENU_MP_MAIN("/menu/MenuMPMain", 3, 4, MENU_MAIN.ordinal()),
    MENU_MP_HOST("/menu/MenuMPHost", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal()),
    MENU_MP_JOIN("/menu/MenuMPJoin", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_MAIN.ordinal()),
    /*5*/MENU_MP_WAIT_CLIENT("/menu/MenuMPWaitingClients", NO_LEVEL_CREATED, NO_LEVEL_CREATED, MENU_MP_HOST.ordinal()),
    /*6*/LEVEL_0("/levels/level0", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED),
    /*7*/LEVEL_1("/levels/level1", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED),
    /*8*/LEVEL_2("/levels/level2", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED),
    /*9*/LEVEL_3("/levels/level3", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED),
    /*10*/LEVEL_4("/levels/level4", NO_LEVEL_CREATED, NO_LEVEL_CREATED, NO_LEVEL_CREATED);

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

            //choice 0-1-2-3-4
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