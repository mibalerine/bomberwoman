package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.ConsoleColors;

/**
 * Created by codecadet on 06/11/17.
 */
public class GameObject {

    private Position position = new Position();

    private String representation;

    private String color;

    public GameObject(String representation) {

        this(representation, 0, 0);
    }

    public GameObject(String representation, int x, int y) {

        this(representation, x, y, null);
    }

    public GameObject(String representation, int x, int y, String color) {

        this.representation = representation;
        setPosition(x, y);
        this.color = color;
    }

    public static boolean isGameObject(int id) {

        return id >= 0 && id < GameObjectType.values().length;
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

    public String getDrawInfo() {

        if(color == null) {

            return representation;
        }

        return color + representation + ConsoleColors.RESET;
    }
}