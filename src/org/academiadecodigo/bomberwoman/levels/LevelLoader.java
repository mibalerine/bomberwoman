package org.academiadecodigo.bomberwoman.levels;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.MenuSelect;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by miro on 07/11/2017.
 */
public class LevelLoader {

    String path;
    private int width;
    private int height;
    private List<GameObject> letters = Collections.synchronizedList(new ArrayList<>());
    private MenuSelect menuSelect;

    public LevelLoader(String path) {

        this.path = path;
        try {

            init();
        }
        catch(FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    private void init() throws FileNotFoundException {

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
                    letters.add(menuSelect);
                }
                else {

                    letters.add(new GameObject(cells[x][y], x, y));
                }
            }
        }
    }

    public int getWidth() {

        return width;
    }

    public int getHeight() {

        return height;
    }

    public List<GameObject> getLetters() {

        return letters;
    }

    public MenuSelect getMenuSelect() {

        return menuSelect;
    }
}