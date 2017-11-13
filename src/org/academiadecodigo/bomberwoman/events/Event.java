package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

/**
 * Created by miro on 06/11/2017.
 */
public abstract class Event {

    public static final String SEPARATOR = " ";
    public static final String EVENT_IDENTIFIER = "ev";


    private EventType eventType;

    public Event(EventType eventType) {

        this.eventType = eventType;
    }

    public static boolean isEvent(String string) {

        //VALID: ev<&>0<&>0<&>x<&>y
        //event, eventType.ordinal(), triggerID(?), posX, posY

        String[] stringComponents = string.split(SEPARATOR);
        if(stringComponents.length < 2) {

            return false;
        }

        if(!stringComponents[0].equals(EVENT_IDENTIFIER)) {

            return false;
        }

        try {

            EventType e = EventType.values()[Integer.parseInt(stringComponents[1])];
            return true;
        }
        catch(NumberFormatException | ArrayIndexOutOfBoundsException e) {

            return false;
        }
    }

    @Override
    public String toString() {

        return "ev" + Event.SEPARATOR + eventType.ordinal();
    }

    public EventType getEventType() {

        return eventType;
    }

    public String[] getInfo() {

        return toString().split(Event.SEPARATOR);
    }
}