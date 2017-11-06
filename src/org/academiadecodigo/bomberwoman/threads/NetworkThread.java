package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

import java.io.*;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by codecadet on 06/11/17.
 */
public class NetworkThread implements Runnable {

    private Socket clientSocket;
    private BufferedReader clientReader;
    private PrintWriter clientWriter;
    private final Vector<GameObject> gameObjects;

    @Override
    public void run() {

        new Thread(new readerThread()).start();

    }

    public NetworkThread(Vector<GameObject> gameObjects) {

        this.gameObjects = gameObjects;
    }


    public void establishConnection(String ipAdress) {

        try {

            clientSocket = new Socket(ipAdress, Constants.PORT);
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendMessage(String message) {

        if(!clientSocket.isClosed()) {

            System.out.println("The Socket for client " + Thread.currentThread().getId() + "is closed!"
                    + "\n Remember to call establishConnection()");

            return;
        }

        clientWriter.write(message);
        clientWriter.flush();
    }


    private class readerThread implements Runnable {

        @Override
        public void run() {

            while(!clientSocket.isClosed()) {

                try {

                    String line = clientReader.readLine();

                    if(line != null) {

                        //TODO: stuff

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
