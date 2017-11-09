package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;

/**
 * Created by codecadet on 09/11/17.
 */
public class PlayerMoveEvent extends Event {

    private Player player;
    private Direction direction;

    public PlayerMoveEvent(Player player, Direction direction) {
        super(EventType.PLAYER_MOVE);

        this.player = player;
        this.direction = direction;
    }

    @Override
    public String toString() {

        return super.toString() + Event.SEPARATOR + GameObjectType.PLAYER.ordinal() + Event.SEPARATOR +
                player.getId() + Event.SEPARATOR +
                (player.getX() + direction.getHorizontal()) + Event.SEPARATOR +
                (player.getY() + direction.getVertical());
    }
}
