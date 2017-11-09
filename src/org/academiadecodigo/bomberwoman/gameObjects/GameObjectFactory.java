package org.academiadecodigo.bomberwoman.gameObjects;

/**
 * Created by miro on 06/11/2017.
 */
public class GameObjectFactory {

    public static GameObject byType(int id, GameObjectType gameObjectType, int x, int y) {

        switch(gameObjectType) {

            case PLAYER:
                return new Player(id, x, y);

            case BRICK:
                return new Brick(id, x, y);

            case WALL:
                return new Wall(id, x, y);

            default:
                return null;
        }
    }
}