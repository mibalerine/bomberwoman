package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.control.MenuSelect;
import org.academiadecodigo.bomberwoman.gameObjects.control.PlayerPointer;
import org.academiadecodigo.bomberwoman.gameObjects.control.UserInput;

import java.util.Map;
import java.util.Stack;

/**
 * Created by miro on 10/11/2017.
 */
public class SpecialObjectHolder {

    private MenuSelect menuSelect;

    private UserInput userInput;

    private PlayerPointer playerPointer;

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

    public void update() {

        if(menuSelect == null) {

            return;
        }

        menuSelect.update();
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

        if(menuSelect.getY() + y < menuSelect.getOriginalY()) {

            y = 4;
        }

        if(menuSelect.getY() + y > menuSelect.getOriginalY() + 4) {

            y = -4;
        }

        menuSelect.translate(x, y);
    }

    private Stack<GameObject> inputNumbers = new Stack<>();

    public void erase(Map<Integer, GameObject> letters) {

        if(userInput == null || inputNumbers.isEmpty()) {

            return;
        }

        GameObject gameObject = inputNumbers.pop();

        if(gameObject != null) {

            letters.remove(gameObject.getId());
        }

        userInput.translate(-1, 0);
    }

    public int inputNumber(int num, int id, Map<Integer, GameObject> letters) {

        if(userInput == null || userInput.getMaxTranslation() + 1 <= inputNumbers.size()) {

            return id;
        }

        GameObject gameObject = new GameObject(id, num + "", userInput.getX(), userInput.getY() - 1);
        letters.put(id++, gameObject);
        inputNumbers.add(gameObject);
        userInput.translate(1, 0);
        return id;
    }
}