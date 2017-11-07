package org.academiadecodigo.bomberwoman.events;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

/**
 * Created by miro on 06/11/2017.
 */
public abstract class Event {

    private GameObject gameObject;

    private EventType eventType;

    public Event(GameObject gameObject, EventType eventType) {

        this.gameObject = gameObject;
        this.eventType = eventType;
    }

    public GameObject getTrigger() {

        return gameObject;
    }

    public EventType getEventType() {

        return eventType;
    }
}