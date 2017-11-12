package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.threads.ServerThread;

/**
 * Created by codecadet on 11/11/2017.
 */
public class Bomb extends GameObject{

    private int blastRadius;

    public Bomb(int id, int x, int y) {

        this(id, x, y, Constants.BOMB_RADIUS);
    }

    public Bomb (int id, int x, int y, int blastRadius) {
        super(id, GameObjectType.BOMB.getDrawChar(), x, y);
        this.blastRadius = blastRadius;
    }

    public void explode() {

        ServerThread serverThread = Game.getInstance().getServerThread();

        serverThread.removeObject(getId());
        serverThread.explode(getX(), getY(), blastRadius );
    }

    public void setBlastRadius(int blastRadius) {

        this.blastRadius = blastRadius;
    }
}
