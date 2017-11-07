package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 06/11/2017.
 */
public abstract class Event {

    public static final String SEPARATOR = "<&>";
    public static final String EVENT_IDENTIFIER = "ev";

    private GameObject gameObject;

    private EventType eventType;

    public Event(GameObject gameObject, EventType eventType) {

        this.gameObject = gameObject;
        this.eventType = eventType;
    }

    public static boolean isEvent(String string) {

        //VALID: ev<&>0<&>0<&>x<&>y
        //event, eventType.ordinal(), triggerID(?), posX, posY

        String[] stringComponents = string.split(SEPARATOR);
        if(stringComponents.length < 3) {

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

    public GameObject getTrigger() {

        return gameObject;
    }

    public EventType getEventType() {

        return eventType;
    }
}