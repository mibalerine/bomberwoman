package org.academiadecodigo.bomberwoman.threads;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by codecadet on 06/11/17.
 */
public class ServerThread implements Runnable {

    private int numberOfPlayers;
    private Socket[] clientConnections;
    private ServerSocket serverSocket;

    public ServerThread(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        clientConnections = new Socket[numberOfPlayers];
    }

    @Override
    public void run() {

        waitClientConnections();
    }

    private void waitClientConnections() {

        int numberOfConnections = 0;

        while (numberOfConnections < numberOfPlayers) {

            try {

                serverSocket = new ServerSocket(8080);
                clientConnections[numberOfConnections] = serverSocket.accept();

            } catch (IOException e) {

            }

            numberOfConnections++;
        }
    }
}
