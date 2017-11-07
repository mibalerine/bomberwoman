package org.academiadecodigo.bomberwoman;

import org.academiadecodigo.bomberwoman.threads.ServerThread;

/**
 * Created by codecadet on 06/11/17.
 */
public class Main {

    public static void main(String[] args) {

        //new Game().start();
        new Thread(new ServerThread(1)).start();
    }
}