package org.academiadecodigo.bomberwoman.threads.input;

import org.academiadecodigo.bomberwoman.direction.Direction;

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
    TAB(9),
    BACKSPACE(127),
    NUM_0(48),
    NUM_1(49),
    NUM_2(50),
    NUM_3(51),
    NUM_4(52),
    NUM_5(53),
    NUM_6(54),
    NUM_7(55),
    NUM_8(56),
    NUM_9(57);

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

    public static boolean isNumber(Keys key) {

        return key.toString().contains("NUM");
    }

    public Direction toDirection() {

        return Direction.valueOf(this.toString());
    }
}