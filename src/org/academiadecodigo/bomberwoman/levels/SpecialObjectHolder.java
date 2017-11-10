package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.control.MenuSelect;
import org.academiadecodigo.bomberwoman.gameObjects.control.PlayerPointer;
import org.academiadecodigo.bomberwoman.gameObjects.control.UserInput;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * Created by miro on 10/11/2017.
 */
public class SpecialObjectHolder {

    private MenuSelect menuSelect;

    private UserInput userInput;

    private PlayerPointer playerPointer;

    private Stack<GameObject> inputNumbers = new Stack<>();

    public MenuSelect getMenuSelect() {

        return menuSelect;
    }

    public void setMenuSelect(MenuSelect menuSelect) {

        this.menuSelect = menuSelect;
    }

    public UserInput getUserInput() {

        return userInput;
    }

    public void setUserInput(UserInput userInput) {

        this.userInput = userInput;
    }

    public PlayerPointer getPlayerPointer() {

        return playerPointer;
    }

    public void setPlayerPointer(PlayerPointer playerPointer) {

        this.playerPointer = playerPointer;
    }

    public int choice() {

        if(menuSelect == null) {

            return 2;
        }

        return menuSelect.choice();
    }

    public void moveSelectionBy(int x, int y) {

        if(menuSelect == null) {

            return;
        }

        menuSelect.translate(x, y);
    }

    void erase(Map<Integer, GameObject> letters) {

        if(userInput == null) {

            return;
        }

        if(userInput.underADot(-1)) {

            userInput.setPosition(userInput.getX() - 1, userInput.getY());
            erase(letters);
            return;
        }

        GameObject gameObject = getObjectAct(letters.values(), userInput.getX() - 1, userInput.getY() - 1);

        if(gameObject == null) {

            return;
        }

        gameObject.setRepresentation("_");
        userInput.translate(-1, 0);
    }

    int inputNumber(int num, int id, Map<Integer, GameObject> letters) {

        if(userInput == null || !userInput.canMove()) {

            return id;
        }

        if(underDot(letters.values(), userInput.getX(), userInput.getY() - 1)) {

            userInput.setPosition(userInput.getX() + 1, userInput.getY());
        }

        GameObject gameObject = getObjectAct(letters.values(), userInput.getX(), userInput.getY() - 1);

        if(gameObject == null) {

            return id;
        }

        gameObject.setRepresentation(num + "");
        userInput.translate(1, 0);

        return id;
    }

    private String getStringAt(Collection<GameObject> gameObjects, int x, int y) {

        GameObject go = getObjectAct(gameObjects, x, y);

        if(go == null) {

            return " ";
        }

        return go.getRepresentation();
    }

    private boolean underDot(Collection<GameObject> gameObjects, int x, int y) {

        return getStringAt(gameObjects, x, y).equals(".");
    }

    private GameObject getObjectAct(Collection<GameObject> gameObjects, int x, int y) {

        Iterator<GameObject> iterator = gameObjects.iterator();
        while(iterator.hasNext()) {

            GameObject go = iterator.next();
            if(go.getX() == x && go.getY() == y) {

                return go;
            }
        }

        return null;
    }
}