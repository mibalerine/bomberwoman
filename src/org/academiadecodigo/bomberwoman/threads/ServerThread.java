package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.events.EventType;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class ServerThread implements Runnable {

    private ServerSocket serverSocket;

    private int numberOfPlayers;

    private Socket[] clientConnections;

    private ExecutorService threadPool;

    private final Map<Integer, GameObject> gameObjectMap = new Hashtable<>();

    private int id;

    public ServerThread(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        clientConnections = new Socket[numberOfPlayers];
        threadPool = Executors.newFixedThreadPool(numberOfPlayers);
    }

    @Override
    public void run() {

        try {

            serverSocket = new ServerSocket(Constants.PORT);
        } catch (IOException e) {

            e.printStackTrace();
        }

        waitClientConnections();
        startGame();
    }

    private void waitClientConnections() {

        int numberOfConnections = 0;

        while (numberOfConnections < numberOfPlayers) {

            try {

                clientConnections[numberOfConnections] = serverSocket.accept();
                System.out.println("Client connected");
                threadPool.submit(new ClientDispatcher(clientConnections[numberOfConnections]));
            } catch (IOException e) {

            }

            numberOfConnections++;
        }
    }

    private void startGame() {

        broadcast("start");
    }

    private void broadcast(String message) {

        for (Socket s : clientConnections) {

            try {

                PrintWriter out = new PrintWriter(s.getOutputStream());

                out.write(message);
                out.flush();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private class ClientDispatcher implements Runnable {

        private Socket clientConnection;

        private ClientDispatcher(Socket clientConnection) {

            this.clientConnection = clientConnection;
        }

        @Override
        public void run() {

            while (!clientConnection.isClosed()) {

                try {

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

                    String line = in.readLine();
                    if (line != null) {

                        if (!Event.isEvent(line)) {

                            continue;
                        }

                        handleEvent(line.split(Event.SEPARATOR));
                    }
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }


    public void handleEvent(String[] eventInfo) {
        int eventId = Integer.parseInt(eventInfo[1]);

        EventType eType = EventType.values()[eventId];

        switch (eType) {

            case OBJECT_SPAWN:
                int objectTypeNum = Integer.parseInt(eventInfo[2]);

                if (!Utils.isNumber(eventInfo[3]) || !Utils.isNumber(eventInfo[4])) {
                    return;
                }
                int x = Integer.parseInt(eventInfo[3]);
                int y = Integer.parseInt(eventInfo[4]);

                synchronized (gameObjectMap) {
                    GameObjectType goType = GameObjectType.values()[objectTypeNum];
                    gameObjectMap.put(id++, GameObjectFactory.byType(goType, x, y));
                }
                break;
        }

    }
}