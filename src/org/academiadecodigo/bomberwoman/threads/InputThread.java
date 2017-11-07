package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.threads.input.Keys;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by codecadet on 06/11/17.
 */
public class InputThread implements Runnable {

    private Reader reader;
    private Game game;

    public InputThread(Game game) {

        reader = new InputStreamReader(System.in);
        this.game = game;
    }

    @Override
    public void run() {

        while(true) {
            try {

                int keyInt = reader.read();
                game.keyPressed(Keys.getKeyByInt(keyInt));
                //System.out.println("KEY PRESSED : " + keyInt);
            }
            catch(IOException e) {

                e.printStackTrace();
            }
            finally {

                try {

                    reader.close();
                }
                catch(IOException e) {

                    e.printStackTrace();
                }
            }
        }

    }
}
