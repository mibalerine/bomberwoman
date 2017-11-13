package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by codecadet on 09/11/17.
 */
public class ObjectMoveEvent extends Event {

    private GameObject gameObject;

    private int xMov;

    private int yMov;

    public ObjectMoveEvent(GameObject gameObject, Direction direction) {

        this(gameObject, gameObject.getX() + direction.getHorizontal(), gameObject.getY() + direction.getVertical());
    }

    public ObjectMoveEvent(GameObject gameObject, int xMov, int yMov) {

        super(EventType.OBJECT_MOVE);

        this.gameObject = gameObject;

        this.xMov = xMov;

        this.yMov = yMov;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + gameObject.getId() + Event.SEPARATOR + (xMov) + Event.SEPARATOR + (yMov);
    }
}
