package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
<<<<<<< HEAD
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.events.EventType;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
import org.academiadecodigo.bomberwoman.events.PlayerAssignEvent;
=======
import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.events.EventType;
import org.academiadecodigo.bomberwoman.events.ObjectSpawnEvent;
>>>>>>> master
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.server.ServerEventHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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

    private Map<Integer, GameObject> gameObjectMap;

    private Integer id;

    private final int[][] PLAYER_SPAWN_POSITIONS = {
            { 1, 1 },
            { Game.WIDTH - 2, 1 },
            { 1, Game.HEIGHT - 2 },
            { Game.WIDTH - 2, Game.HEIGHT - 2}
    };

    private int nextPlayerPosition = 0;

    public ServerThread(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        clientConnections = new Socket[numberOfPlayers];
        threadPool = Executors.newFixedThreadPool(numberOfPlayers);
        gameObjectMap = new Hashtable<>();
        id = 4000;
    }

    @Override
    public void run() {

        try {

            serverSocket = new ServerSocket(Constants.PORT);
        }
        catch(IOException e) {

            e.printStackTrace();
        }

        waitClientConnections();

        createGameObjects();

        startGame();
    }

    private void waitClientConnections() {

        int numberOfConnections = 0;

        while(numberOfConnections < numberOfPlayers) {

            try {

                clientConnections[numberOfConnections] = serverSocket.accept();
                System.out.println("Client connected");
                threadPool.submit(new ClientDispatcher(clientConnections[numberOfConnections]));

                synchronized (gameObjectMap) {
                    sendMessage(clientConnections[numberOfConnections], new PlayerAssignEvent(id).toString());

                    int[] playerPosition = PLAYER_SPAWN_POSITIONS[nextPlayerPosition++];
                    gameObjectMap.put(id, GameObjectFactory.byType(id, GameObjectType.PLAYER, playerPosition[0], playerPosition[1]));
                    id++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            numberOfConnections++;
        }
    }

    private void startGame() {

        broadcast("start");
    }

    private void sendMessage(Socket clientSocket, String message) {

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            out.write(message + "\n");
            out.flush();

        } catch (IOException e) {
            System.out.println("Socket closed: " + e.getMessage());
        }
    }

    public void broadcast(String message) {

        System.out.println("Broadcast: " + message);
        for(Socket s : clientConnections) {

            try {

                PrintWriter out = new PrintWriter(s.getOutputStream());

                out.write(message + "\n");
                out.flush();
            }
            catch(IOException e) {

                e.printStackTrace();
            }
        }
    }

    public Map<Integer, GameObject> getGameObjectMap() {

        return gameObjectMap;
    }

    public void handleEvent(String[] eventInfo) {

        int eventId = Integer.parseInt(eventInfo[1]);

        EventType eType = EventType.values()[eventId];

        switch(eType) {

            case OBJECT_SPAWN:

                synchronized(gameObjectMap) {
                    id = ServerEventHandler.handleObjectSpawnEvent(eventInfo, id, this);
                }

                break;

            case OBJECT_MOVE:

                synchronized(gameObjectMap) {
                    ServerEventHandler.handleObjectMoveEvent(eventInfo, this);
                }
                break;
        }

    }

    private void createGameObjects() {

        synchronized (gameObjectMap) {

            for (GameObject go : gameObjectMap.values()) {
                broadcast(new ObjectSpawnEvent(GameObjectType.PLAYER, go.getId(), go.getX(), go.getY()).toString());
            }

        }

        try {

            InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(ScreenHolder.LEVEL_1.getFilePath()));
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int i = 0;

            while((line = bf.readLine()) != null) {

                String[] chars = line.split("");

                for(int j = 0; j < chars.length; j++) {

                    createObject(chars[j], i, j);

                }

                i++;
            }

        }
        catch(IOException e) {
            System.out.println("Could not read file: " + e.getMessage());
        }
    }

    private void createObject(String objectChar, int x, int y) {

        synchronized(gameObjectMap) {

            switch(objectChar) {

                case Constants.BRICK_CHAR:
                    gameObjectMap.put(id, GameObjectFactory.byType(id, GameObjectType.BRICK, x, y));
                    broadcast(new ObjectSpawnEvent(GameObjectType.BRICK, id, x, y).toString());
                    break;

                case Constants.PLAYER_CHAR:
                    gameObjectMap.put(id, GameObjectFactory.byType(id, GameObjectType.PLAYER, x, y));
                    broadcast(new ObjectSpawnEvent(GameObjectType.PLAYER, id, x, y).toString());

                    break;

                case Constants.WALL_CHAR:
                    gameObjectMap.put(id, GameObjectFactory.byType(id, GameObjectType.WALL, x, y));
                    broadcast(new ObjectSpawnEvent(GameObjectType.WALL, id, x, y).toString());
                    break;

                default:
                    return;
            }

            id++;
        }
    }


    private class ClientDispatcher implements Runnable {

        private Socket clientConnection;

        private ClientDispatcher(Socket clientConnection) {

            this.clientConnection = clientConnection;
        }

        @Override
        public void run() {

            while(!clientConnection.isClosed()) {

                try {

                    BufferedReader in = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

                    String line = in.readLine();
                    if(line != null) {

                        if(!Event.isEvent(line)) {
                            continue;
                        }

                        handleEvent(line.split(Event.SEPARATOR));
                    }
                }
                catch(IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}