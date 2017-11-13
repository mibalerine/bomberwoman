package org.academiadecodigo.bomberwoman.events;

/**
 * Created by codecadet on 12/11/17.
 */
public class ServerCloseEvent extends Event{

    public ServerCloseEvent() {
        super(EventType.SERVER_CLOSE);
    }
}
