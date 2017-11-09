package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.ConsoleColors;
import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.MenuSelect;

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

    private MenuSelect menuSelect;

    private LevelFileLocator levelFileLocator;

    public Level(LevelFileLocator levelFileLocator, Map<Integer, GameObject> letters) {

        this.levelFileLocator = levelFileLocator;
        this.path = this.levelFileLocator.getFilePath();
        this.letters = letters;

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

        for(int y = 0; y < cells[0].length; y++) {

            for(int x = 0; x < cells.length; x++) {

                if(cells[x][y] == null || cells[x][y].equals(" ") || cells[x][y].equals("~")) {

                    continue;
                }

                if(cells[x][y].equals(Constants.OBJECT_CONTROL_MENU)) {

                    menuSelect = new MenuSelect(x, y);
                    letters.put(id++, menuSelect);
                }
                else {

                    letters.put(id, new GameObject(id, cells[x][y], x, y));
                    id++;
                }
            }
        }

        if(levelFileLocator == LevelFileLocator.MENU_MP_HOST) {

            if(!setupIP) {

                setupIP = true;
                replaceIP();
            }
        }
    }

    private void replaceIP() {

        try {

            String IP = InetAddress.getLocalHost().getHostAddress();
            int x = menuSelect.getX();
            int y = menuSelect.getY();
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
            menuSelect = null;
        }
        catch(UnknownHostException e) {

            e.printStackTrace();
            Utils.quitGame();
        }
    }

    public void update() {

        if(menuSelect == null) {

            return;
        }

        menuSelect.update();
    }

    public int choice() {

        return menuSelect.choice();
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

    public LevelFileLocator getLevelFileLocator() {

        return levelFileLocator;
    }

    public void moveSelectionBy(int y) {

        if(menuSelect == null) {

            return;
        }

        if(menuSelect.getY() + y < menuSelect.getOriginalY()) {

            y = 4;
        }

        if(menuSelect.getY() + y > menuSelect.getOriginalY() + 4) {

            y = -4;
        }

        menuSelect.translate(0, y);
    }
}