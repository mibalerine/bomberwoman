package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

/**
 * Created by miro on 06/11/2017.
 */
public class ObjectSpawnEvent extends Event {

    private int x;
    private int y;
    private int id;
    private GameObjectType type;

    public ObjectSpawnEvent(GameObjectType gameObjectType, int x, int y) {
        this(gameObjectType, -1, x, y);
    }

    public ObjectSpawnEvent(GameObjectType gameObjectType, int id, int x, int y) {
        super(EventType.OBJECT_SPAWN);

        this.x = x;
        this.y = y;
        this.id = id;
        this.type = gameObjectType;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + type.ordinal() + Event.SEPARATOR + id + Event.SEPARATOR + x + Event.SEPARATOR + y;
    }
}