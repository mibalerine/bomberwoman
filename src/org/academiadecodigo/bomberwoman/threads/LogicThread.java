package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.events.ObjectMoveEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.Player;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.logic.CollisionDetector;

import java.util.Map;

/**
 * Created by codecadet on 06/11/17.
 */
public class LogicThread implements Runnable {

    private NetworkThread networkThread;
    private final Map<Integer, GameObject> gameObjects;
    private int playerId;

    public LogicThread(Map<Integer, GameObject> gameObjectMap) {
        gameObjects = gameObjectMap;
    }

    //TODO: logic thread por enquanto nao faz nada no run()
    @Override
    public void run() {
    }

    public void keyPressed(Keys keyPressed) {

        GameObject go = gameObjects.get(playerId);

        if (go == null) {
            return;
        }

        switch(keyPressed) {

            case UP:
                moveUp(go);
                break;
            case DOWN:
                moveDown(go);
                break;
            case RIGHT:
                moveRight(go);
                break;
            case LEFT:
                moveLeft(go);
                break;
            case PLACE_BOMB:
                placeBomb(go);
                break;
            case ENTER:
                break;
        }
    }

    private void moveUp(GameObject go) {
        if (!checkMove(Direction.UP, go)) {
            return;
        }

        networkThread.sendMessage((new ObjectMoveEvent(go, Direction.UP)).toString());
    }

    private void moveDown(GameObject go) {
        if (!checkMove(Direction.DOWN, go)) {
            return;
        }

        networkThread.sendMessage((new ObjectMoveEvent(go, Direction.DOWN)).toString());
    }

    private void moveRight(GameObject go) {
        if (!checkMove(Direction.RIGHT, go)) {
            return;
        }

        networkThread.sendMessage((new ObjectMoveEvent(go, Direction.RIGHT)).toString());
    }

    private void moveLeft(GameObject go) {
        if (!checkMove(Direction.LEFT, go)) {
            return;
        }

        networkThread.sendMessage((new ObjectMoveEvent(go, Direction.LEFT)).toString());
    }

    private void placeBomb(GameObject go) {

        networkThread.sendMessage((new ObjectSpawnEvent(GameObjectType.BOMB, go.getX(), go.getY())).toString());
    }

    private boolean checkMove(Direction direction, GameObject go) {

        int vertical = direction.getVertical();
        int horizontal = direction.getHorizontal();

        return CollisionDetector.canMove(go.getX() + horizontal, go.getY() + vertical);
    }

    public void setNetworkThread(NetworkThread networkThread) {
        this.networkThread = networkThread;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}