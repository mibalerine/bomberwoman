package org.academiadecodigo.bomberwoman.gameObjects;

/**
 * Created by codecadet on 09/11/17.
 */
public class Wall extends GameObject {
    public Wall(int id, int x, int y) {
        super(id, GameObjectType.WALL.getDrawChar(), x, y);
    }
}
