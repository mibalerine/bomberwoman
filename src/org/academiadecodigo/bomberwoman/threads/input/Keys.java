package org.academiadecodigo.bomberwoman.threads.input;

/**
 * Created by codecadet on 06/11/17.
 */
public enum Keys {

    NULL(-100),
    UP(65),
    DOWN(66),
    RIGHT(67),
    LEFT(68),
    PLACE_BOMB(32),
    QUIT_GAME(3),
    ENTER(13),
    BACKSPACE(127);

    private int num;

    Keys(int key) {

        num = key;
    }

    public static Keys getKeyByInt(int keyNum) {

        for(Keys key : Keys.values()) {
            if(keyNum == key.num) {
                return key;
            }
        }
        return NULL;
    }
}