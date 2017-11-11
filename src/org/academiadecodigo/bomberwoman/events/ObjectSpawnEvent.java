package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

/**
 * Created by miro on 06/11/2017.
 */
public class ObjectSpawnEvent extends Event {

    private int x;
    private int y;
    private int id;
    private GameObjectType type;
    private boolean shouldRefresh;

    /**
     *
     * @param gameObjectType
     * @param x
     * @param y
     *
     * Client Side Event
     *
     */
    public ObjectSpawnEvent(GameObjectType gameObjectType, int x, int y) {
        this(gameObjectType, -1, x, y, false);
    }


    /**
     *  @param gameObjectType
     * @param id
     * @param x
     * @param y
*@param shouldRefresh
     */
    public ObjectSpawnEvent(GameObjectType gameObjectType, int id, int x, int y, boolean shouldRefresh) {
        super(EventType.OBJECT_SPAWN);

        this.x = x;
        this.y = y;
        this.id = id;
        this.type = gameObjectType;

        this.shouldRefresh = shouldRefresh;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + type.ordinal() + Event.SEPARATOR + id + Event.SEPARATOR + x + Event.SEPARATOR + y + Event.SEPARATOR + shouldRefresh;
    }
}