package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.events.PlayerMoveEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.util.Map;

/**
 * Created by codecadet on 06/11/17.
 */
public class LogicThread implements Runnable {

    private NetworkThread networkThread;
    private final Map<Integer, GameObject> gameObjects;
    private Player player;

    public LogicThread(NetworkThread networkThread, Map<Integer, GameObject> gameObjectMap, Player player) {
        this.networkThread = networkThread;
        gameObjects = gameObjectMap;
        this.player = player;
    }

    @Override
    public void run() {


    }

    public void keyPressed(Keys keyPressed) {

        switch(keyPressed) {

            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case PLACE_BOMB:
                new ObjectSpawnEvent(GameObjectType.PLAYER, 10, 10);
                break;
            case ENTER:
                break;
        }
    }

    private void moveUp() {
        if (!checkMove(Direction.UP)) {
            return;
        }

        networkThread.sendMessage((new PlayerMoveEvent(player, Direction.UP)).toString());
    }

    private void moveDown() {
        if (!checkMove(Direction.DOWN)) {
            return;
        }

        networkThread.sendMessage((new PlayerMoveEvent(player, Direction.DOWN)).toString());
    }

    private void moveRight() {
        if (!checkMove(Direction.RIGHT)) {
            return;
        }

        networkThread.sendMessage((new PlayerMoveEvent(player, Direction.RIGHT)).toString());
    }

    private void moveLeft() {
        if (!checkMove(Direction.LEFT)) {
            return;
        }

        networkThread.sendMessage((new PlayerMoveEvent(player, Direction.LEFT)).toString());
    }

    private boolean checkMove(Direction direction) {

        int vertical = direction.getVertical();
        int horizontal = direction.getHorizontal();

        if (!(player.getX() + horizontal > 0) || !(player.getX() + horizontal < Game.WIDTH) ||
                !(player.getY() + vertical > 0) || !(player.getY() + vertical < Game.HEIGHT)) {
            return false;
        }

        synchronized (gameObjects) {

            for (GameObject go : gameObjects.values()) {

                if (player.getX() + horizontal == go.getX() && player.getY() + vertical == go.getY()) {
                    return false;
                }
            }

            return true;
        }
    }
}