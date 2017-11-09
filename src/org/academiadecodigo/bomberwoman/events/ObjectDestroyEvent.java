package org.academiadecodigo.bomberwoman.events;

/**
 * Created by codecadet on 09/11/17.
 */
public class ObjectDestroyEvent extends Event {

    private int id;

    public ObjectDestroyEvent(int id) {
        super(EventType.OBJECT_DESTROY);

        this.id = id;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + id;
    }
}
