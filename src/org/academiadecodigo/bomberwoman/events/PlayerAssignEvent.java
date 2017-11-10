package org.academiadecodigo.bomberwoman.events;

/**
 * Created by codecadet on 10/11/17.
 */
public class PlayerAssignEvent extends Event {

    private int id;

    public PlayerAssignEvent(int id) {
        super(EventType.PLAYER_ID);
        this.id = id;
    }

    @Override
    public String toString() {
        return super.toString() + Event.SEPARATOR + id;
    }
}
