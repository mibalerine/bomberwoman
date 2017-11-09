package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;

/**
 * Created by codecadet on 09/11/17.
 */
public class Player extends GameObject{
    public Player(int id, int x, int y) {
        super(id, Constants.PLAYER_CHAR, x, y);
    }
}
