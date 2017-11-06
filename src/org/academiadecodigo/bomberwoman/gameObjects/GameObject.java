package org.academiadecodigo.bomberwoman.gameObjects;

/**
 * Created by codecadet on 06/11/17.
 */
public class GameObject {

    private Position position = new Position();

    private String representation;

    private int objectId;

    public GameObject(String representation) {

        this(representation, 0, 0);
    }

    public GameObject(String representation, int x, int y) {

        this.representation = representation;
        setPosition(x, y);
    }

    public int getX() {

        return position.getX();
    }

    public int getY() {

        return position.getY();
    }

    public void setPosition(int x, int y) {

        position.setPos(x, y);
    }

    public void translate(int x, int y) {

        position.translate(x, y);
    }

    public String getDrawChar() {

        return representation;
    }
}