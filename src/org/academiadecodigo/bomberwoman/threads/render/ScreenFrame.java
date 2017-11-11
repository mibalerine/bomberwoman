package org.academiadecodigo.bomberwoman.threads.render;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by miro on 04-11-2017.
 */
class ScreenFrame {

    private StringBuilder content = new StringBuilder("");

    private volatile String[][] cells;

    ScreenFrame(int width, int height) {

        cells = new String[width][height];

        clearBoard();
    }

    private void clearBoard() {

        synchronized(cells) {

            for(int x = 0; x < width(); x++) {

                for(int y = 0; y < height(); y++) {

                    String cell = " ";
                    if(isCorner(x, y) || isSide(x) || isVert(y)) {

                        cell = Constants.CORNER_CHAR;
                    }

                    cells[x][y] = cell;
                }
            }
        }
    }

    void update() {

        updateContent();

        clearBoard();
    }

    private synchronized void updateContent() {

        synchronized(cells) {

            content.setLength(0);        //This clears the content of the StringBuilder
            content.append("\r");
            for(int y = 0; y < height(); y++) {

                for(int x = 0; x < width(); x++) {

                    content.append(cells[x][y]);

                    if(x == width() - 1) {

                        content.append("\r");
                        content.append(System.lineSeparator());
                    }
                }
            }
        }
    }

    void putStringAt(String objectString, int x, int y) {

        x = Utils.clamp(x, 0, Game.WIDTH);
        y = Utils.clamp(y, 0, Game.HEIGHT);

        cells[x][y] = objectString;
    }

    String getContent() {

        return content.toString();
    }

    private boolean isCorner(int x, int y) {

        return x == 0 && y == 0 || x == 0 && y == height() - 1 || x == width() - 1 && y == 0 || x == width() - 1 && y == height() - 1;
    }

    private boolean isSide(int x) {

        return x == 0 || x == width() - 1;
    }

    private boolean isVert(int y) {

        return y == 0 || y == height() - 1;
    }

    int width() {

        if(cells == null) {

            return 0;
        }
        return cells.length;
    }

    int height() {

        if(cells == null) {

            return 0;
        }
        return cells[0].length;
    }
}