package org.academiadecodigo.bomberwoman.gameObjects;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.events.ObjectDestroyEvent;
import org.academiadecodigo.bomberwoman.threads.ServerThread;

/**
 * Created by codecadet on 11/11/2017.
 */
public class Bomb extends GameObject{

    public Bomb(int id, int x, int y) {
        super(id, GameObjectType.BOMB.getDrawChar(), x, y);
    }

    public void explode() {

        ServerThread serverThread = Game.getInstance().getServerThread();

        serverThread.removeObject(getId());
    }


}
