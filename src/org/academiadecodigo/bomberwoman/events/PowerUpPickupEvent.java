package org.academiadecodigo.bomberwoman.events;

/**
 * Created by miro on 12/11/2017.
 */
public class PowerUpPickupEvent extends Event{

    private int id;

    private int playerId;

    public PowerUpPickupEvent(int playerId, int id) {

        super(EventType.POWERUP_PICKUP);

        this.id = id;

        this.playerId = playerId;
    }

    @Override
    public String toString() {

        //ev 5 id playerId
        return super.toString() + Event.SEPARATOR + id + Event.SEPARATOR + playerId;
    }
}