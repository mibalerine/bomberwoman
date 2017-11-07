package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by miro on 07/11/2017.
 */
public class GameScreen extends Screen {

    private List<GameObject> gameObjects = Collections.synchronizedList(new ArrayList<>());

    public GameScreen(int width, int height) {

        super(width, height);
    }
}