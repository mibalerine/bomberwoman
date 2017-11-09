package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.MenuSelect;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by miro on 07/11/2017.
 */
public class Level {

    private final Map<Integer, GameObject> letters;

    String path;

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

                    String[] chars = line.split(Constants.LEVEL_SEPARATOR);
                    width = chars.length;
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

        int id = 0;

        String[][] cells = new String[width][height];
        int lineIndex = 0;
        for(String line : lineList) {

            String[] chars = line.split(Constants.LEVEL_SEPARATOR);
            for(int i = 0; i < chars.length; i++) {

                cells[i][lineIndex] = chars[i] == null ? " " : chars[i];
            }
            lineIndex++;
        }

        for(int y = 0; y < cells[0].length; y++) {

            for(int x = 0; x < cells.length; x++) {

                if(cells[x][y] == null || cells[x][y].equals(" ")) {

                    continue;
                }

                if(cells[x][y].equals(Constants.OBJECT_CONTROL_MENU)) {

                    menuSelect = new MenuSelect(x, y);
                    letters.put(id++, menuSelect);
                }
                else {

                    letters.put(id++, new GameObject(cells[x][y], x, y));
                }
            }
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

        System.out.println("MOVE BY " + y);
        if(menuSelect.getY() + y < menuSelect.getOriginalY()) {

            y = 4;
        }

        if(menuSelect.getY() + y > menuSelect.getOriginalY() + 4) {

            y = -4;
        }

        menuSelect.translate(0, y);
    }
}