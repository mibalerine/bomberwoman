package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.gameObjects.control.MenuSelect;
import org.academiadecodigo.bomberwoman.gameObjects.control.PlayerPointer;
import org.academiadecodigo.bomberwoman.gameObjects.control.UserInput;
import org.academiadecodigo.bomberwoman.threads.ServerThread;
import org.academiadecodigo.bomberwoman.threads.input.Keys;
import org.academiadecodigo.bomberwoman.threads.render.Screen;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by miro on 07/11/2017.
 */
public class Level {

    private final Map<Integer, GameObject> letters;

    private int id = 0;

    private String path;

    private boolean setupIP;

    private int width;

    private int height;

    private SpecialObjectHolder specialObjectHolder;

    private ScreenHolder screenHolder;

    public Level(ScreenHolder screenHolder, Map<Integer, GameObject> letters) {

        this.screenHolder = screenHolder;
        this.path = this.screenHolder.getFilePath();
        this.letters = letters;
        specialObjectHolder = new SpecialObjectHolder();

        try {

            init();
        }
        catch(FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    private void init() throws FileNotFoundException {

        synchronized(letters) {

            letters.clear();
        }

        InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(path));

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {

            //////////////////////////////////////////////////SETUP THE DIMENSIONS OF THIS FILE
            List<String> lines = new ArrayList<>();
            while((line = reader.readLine()) != null) {

                lines.add(line);
                if(width == 0) {

                    width = line.toCharArray().length;
                }
            }
            height = lines.size();

            populateCells(lines);
        }
        catch(IOException e) {

            e.printStackTrace();
        }
    }

    private void populateCells(List<String> lineList) {

        id = 0;
        String[][] cells = new String[width][height];
        int lineIndex = 0;
        for(String line : lineList) {

            char[] chars = line.toCharArray();

            int i = 0;
            for(char c : chars) {

                cells[i++][lineIndex] = c + "";
            }
            lineIndex++;
        }

        synchronized(letters) {

            for(int y = 0; y < cells[0].length; y++) {

                for(int x = 0; x < cells.length; x++) {

                    if(cells[x][y] == null || cells[x][y].equals(" ") || cells[x][y].equals("~")) {

                        continue;
                    }

                    GameObject gameObject = null;
                    if(cells[x][y].equals(Constants.OBJECT_CONTROL_MENU)) {

                        gameObject = new MenuSelect(id, x, y);
                        specialObjectHolder.setMenuSelect((MenuSelect) gameObject);
                    }
                    else if(cells[x][y].equals(Constants.OBJECT_INPUT_TEXT)) {

                        gameObject = new UserInput(id, x, y, 11);
                        specialObjectHolder.setUserInput((UserInput) gameObject);
                    }
                    else if(cells[x][y].equals(Constants.OBJECT_PLAYER_POINTER)) {

                        gameObject = new PlayerPointer(id, x, y);
                        specialObjectHolder.setPlayerPointer((PlayerPointer) gameObject);
                    }
                    else {

                        gameObject = new GameObject(id, cells[x][y], x, y);
                    }

                    letters.put(id++, gameObject);
                }
            }

            if(screenHolder == ScreenHolder.MENU_MP_HOST) {

                if(!setupIP) {

                    setupIP = true;
                    replaceIP();
                }
            }
        }
    }

    private void replaceIP() {

        try {

            String IP = InetAddress.getLocalHost().getHostAddress();
            int x = specialObjectHolder.getMenuSelect().getX();
            int y = specialObjectHolder.getMenuSelect().getY();
            for(String s : IP.split("\\.")) {

                char[] chars = s.toCharArray();

                for(char c : chars) {

                    letters.put(id, new GameObject(id, c + "", x++, y));
                    id++;
                }
                letters.put(id, new GameObject(id, ".", x++, y));
                id++;
            }
            letters.remove(id - 1);
            specialObjectHolder.setMenuSelect(null);
        }
        catch(UnknownHostException e) {

            e.printStackTrace();
            Utils.quitGame();
        }
    }

    public void update() {

    }

    public int choice() {

        return specialObjectHolder.choice();
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public Map<Integer, GameObject> getLetters() {

        return letters;
    }

    public ScreenHolder getScreenHolder() {

        return screenHolder;
    }

    public void moveSelectionBy(int y) {

        specialObjectHolder.moveSelectionBy(0, y);
    }

    public void erase() {

        synchronized(letters) {

            specialObjectHolder.erase(letters);
        }
    }

    public void inputNumber(int num) {

        synchronized(letters) {

            id = specialObjectHolder.inputNumber(num, id, letters);
        }
    }

    public void pressedEnter(Screen screen, Map<Integer, GameObject> gameObjectMap) {

        //int number = specialObjectHolder.getNumberOnInput();
        if(screenHolder == ScreenHolder.MENU_MP_HOST) {

            host(screen);
        }
        else {

            join(gameObjectMap);
        }
    }

    private void join(Map<Integer, GameObject> gameObjectMap) {

        //
        int initialX = 53;

        StringBuilder ipAddress = new StringBuilder();

        for(int x = 0; x < 15; x++) {

            GameObject gameObject = Utils.getObjectAt(gameObjectMap.values(), initialX + x, 31);

            if(gameObject == null) {

                continue;
            }

            ipAddress.append(gameObject.getRepresentation());
        }

        Game.getInstance().connectTo(ipAddress.toString());
        Game.getInstance().refreshRenderThread();
    }

    private void host(Screen screen) {

        System.out.println("Level.host");
        screen.changeFrame(ScreenHolder.MENU_MP_WAIT_CLIENT, letters);

        Utils.hostAndConnect(2);
    }

    public void pressedKeyOnWaitClient(Keys key, Collection<GameObject> objects) {

        if(key == Keys.PLACE_BOMB) {

            if(!Game.getInstance().getServerThread().allowMorePlayers()) {

                return;
            }

            addNewClient();
        }
        else {

            removeClient(objects);
        }
    }

    private void addNewClient() {

        try {

            Game.getInstance().getServerThread().spawnObject(GameObjectType.BRICK, id++, specialObjectHolder.getPlayerPointer().getX() + 1, specialObjectHolder.getPlayerPointer().getY(), true);
            specialObjectHolder.getPlayerPointer().translate(0, 1);
        }
        catch(NullPointerException e) {

            e.printStackTrace();
        }
    }

    private void removeClient(Collection<GameObject> objects) {

        GameObject gameObject = Utils.getObjectAt(objects, specialObjectHolder.getPlayerPointer().getX() + 1, specialObjectHolder.getPlayerPointer().getY() - 1);

        if(gameObject == null) {

            return;
        }

        specialObjectHolder.getPlayerPointer().translate(0, -1);
        Game.getInstance().getServerThread().removeObject(gameObject.getId());
    }
}