package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.control.MenuSelect;
import org.academiadecodigo.bomberwoman.gameObjects.control.PlayerPointer;
import org.academiadecodigo.bomberwoman.gameObjects.control.UserInput;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

                    if(cells[x][y].equals(Constants.OBJECT_CONTROL_MENU)) {

                        specialObjectHolder.setMenuSelect(new MenuSelect(id, x, y));
                        letters.put(id++, specialObjectHolder.getMenuSelect());
                    }
                    else if(cells[x][y].equals(Constants.OBJECT_INPUT_TEXT)) {

                        specialObjectHolder.setUserInput(new UserInput(id, x, y, 4));
                        letters.put(id++, specialObjectHolder.getUserInput());
                    }
                    else if(cells[x][y].equals(Constants.OBJECT_PLAYER_POINTER)) {

                        specialObjectHolder.setPlayerPointer(new PlayerPointer(id, x, y));
                        letters.put(id++, specialObjectHolder.getPlayerPointer());
                    }
                    else {

                        letters.put(id, new GameObject(id, cells[x][y], x, y));
                        id++;
                    }
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

        specialObjectHolder.update();
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
}