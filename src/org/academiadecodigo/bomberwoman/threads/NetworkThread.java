package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;

/**
 * Created by codecadet on 06/11/17.
 */
public class NetworkThread implements Runnable {

    private final Vector<GameObject> gameObjects;

    private Socket clientSocket;

    private BufferedReader clientReader;

    private PrintWriter clientWriter;

    public NetworkThread(Vector<GameObject> gameObjects) {

        this.gameObjects = gameObjects;
    }

    @Override
    public void run() {

        start();
    }

    public void establishConnection(String ipAdress) {

        try {

            clientSocket = new Socket(ipAdress, Constants.PORT);
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream());

        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }


    public void sendMessage(String message) {

        if(!clientSocket.isClosed()) {

            System.out.println("The Socket for client " + Thread.currentThread().getId() + "is closed!" + "\n Remember to call establishConnection()");

            return;
        }

        clientWriter.write(message);
        clientWriter.flush();
    }

    public void start() {

        while(!clientSocket.isClosed()) {

            try {

                String line = clientReader.readLine();

                if(line != null) {

                    //TODO: stuff

                }

            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
