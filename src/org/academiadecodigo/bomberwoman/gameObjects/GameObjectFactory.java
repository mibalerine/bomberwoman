package org.academiadecodigo.bomberwoman.gameObjects;

/**
 * Created by miro on 06/11/2017.
 */
public class GameObjectFactory {

    public static GameObject byType(int id, GameObjectType gameObjectType, int x, int y) {

        switch(gameObjectType) {

            case PLAYER:
                return new GameObject(id, gameObjectType.getDrawChar(), x, y);

            case BRICK:
            default:
                return null;
        }
    }
}