package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.ObjectMoveEvent;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.logic.CollisionDetector;

import java.util.Map;

/**
 * Created by codecadet on 06/11/17.
 */
public class LogicThread implements Runnable {

    private final Map<Integer, GameObject> gameObjects;

    private NetworkThread networkThread;

    private int playerId;

    public LogicThread(Map<Integer, GameObject> gameObjectMap) {

        gameObjects = gameObjectMap;
    }

    //TODO: logic thread por enquanto nao faz nada no run()
    @Override
    public void run() {

    }

    public void keyPressed(Keys keyPressed) {

        if (keyPressed == Keys.TAB) {
            Game.getInstance().closeClient();
            return;
        }

        GameObject go = gameObjects.get(playerId);

        if(go == null) {
            return;
        }

        switch(keyPressed) {

            case UP:
            case DOWN:
            case RIGHT:
            case LEFT:
                move(go, keyPressed.toDirection());
                break;
            case PLACE_BOMB:
                placeBomb(go);
                break;
            case ENTER:
                break;
        }
    }

    private void move(GameObject go, Direction direction) {

        if(!checkMove(direction, go)) {

            return;
        }

        networkThread.sendMessage((new ObjectMoveEvent(go, direction)).toString());
    }

    private void placeBomb(GameObject go) {

        networkThread.sendMessage((new ObjectSpawnEvent(GameObjectType.BOMB, go.getX(), go.getY())).toString());
    }

    private boolean checkMove(Direction direction, GameObject go) {

        int vertical = direction.getVertical();
        int horizontal = direction.getHorizontal();

        return CollisionDetector.canMove(go.getX() + horizontal, go.getY() + vertical, go.getId(), networkThread);
    }

    public void setNetworkThread(NetworkThread networkThread) {

        this.networkThread = networkThread;
    }

    public void setPlayerId(int playerId) {

        this.playerId = playerId;
    }
}