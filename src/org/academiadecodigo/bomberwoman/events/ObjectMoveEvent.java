package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;

/**
 * Created by codecadet on 09/11/17.
 */
public class ObjectMoveEvent extends Event {

    private GameObject gameObject;
    private Direction direction;

    public ObjectMoveEvent(GameObject gameObject, Direction direction) {
        super(EventType.OBJECT_MOVE);

        this.gameObject = gameObject;
        this.direction = direction;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR  +
                gameObject.getId() + Event.SEPARATOR +
                (gameObject.getX() + direction.getHorizontal()) + Event.SEPARATOR +
                (gameObject.getY() + direction.getVertical());
    }
}
