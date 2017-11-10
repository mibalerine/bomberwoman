package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;

import static org.academiadecodigo.bomberwoman.ConsoleColors.*;

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

    public static GameObject byString(String charString, int id, int x, int y) {

        String color = ConsoleColors.WHITE;
        if(charString.equals(Constants.WALL_CHAR_BLUE)) {

            color = BLUE;
            charString = Constants.WALL_CHAR;
        }
        else if(charString.equals(Constants.BRICK_CHAR)) {

            color = RED;
        }

        return new GameObject(id, charString, x, y, color);
    }
}