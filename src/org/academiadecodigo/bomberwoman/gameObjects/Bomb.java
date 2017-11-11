package org.academiadecodigo.bomberwoman.gameObjects;

/**
 * Created by codecadet on 11/11/2017.
 */
public class Bomb extends GameObject{

    public Bomb(int id, int x, int y) {
        super(id, GameObjectType.BOMB.getDrawChar(), x, y);
    }


}
