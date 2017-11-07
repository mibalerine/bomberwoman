package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

/**
 * Created by miro on 06/11/2017.
 */
public class ObjectSpawnEvent extends Event {

    private int x;
    private int y;

    public ObjectSpawnEvent(GameObjectType gameObjectType, int x, int y) {

        super(GameObjectFactory.byType(gameObjectType, x, y), EventType.OBJECT_SPAWN);

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + "-1" + Event.SEPARATOR + x + Event.SEPARATOR + y;
    }
}