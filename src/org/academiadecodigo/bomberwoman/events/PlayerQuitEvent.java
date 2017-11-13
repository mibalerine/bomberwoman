package org.academiadecodigo.bomberwoman.events;

/**
 * Created by codecadet on 12/11/17.
 */
public class PlayerQuitEvent extends  Event{

    private int playerId;

    public PlayerQuitEvent(int playerId) {
        super(EventType.PLAYER_QUIT);

        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return super.toString() + SEPARATOR + playerId;
    }
}
