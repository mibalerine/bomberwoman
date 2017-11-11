package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.events.*;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectFactory;
import org.academiadecodigo.bomberwoman.gameObjects.GameObjectType;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.server.ClientDispatcher;
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

    private final Map<Integer, GameObject> gameObjectMap;

    private final int[][] PLAYER_SPAWN_POSITIONS = { { 1,
            1 },
            { Game.WIDTH - 2,
                    Game.HEIGHT - 2 },
            { Game.WIDTH - 2,
                    1 },
            { 1,
                    Game.HEIGHT - 2 } };

    private ServerSocket serverSocket;

    private int numberOfPlayers;

    private Socket[] clientConnections;

    private ExecutorService threadPool;

    private Integer id;

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
                threadPool.submit(new ClientDispatcher(clientConnections[numberOfConnections], this));

                synchronized(gameObjectMap) {

                    sendMessage(clientConnections[numberOfConnections], new PlayerAssignEvent(id).toString());

                    int[] playerPosition = PLAYER_SPAWN_POSITIONS[nextPlayerPosition++];
                    gameObjectMap.put(id, GameObjectFactory.byType(id, GameObjectType.PLAYER, playerPosition[0], playerPosition[1]));
                    id++;
                }

            }
            catch(IOException e) {
                e.printStackTrace();
            }

            numberOfConnections++;
        }
    }

    private void startGame() {

        //broadcast("start");
        //TODO change everyone's screen to load the level1.txt
    }

    private void sendMessage(Socket clientSocket, String message) {

        try {
            System.out.println(clientSocket + "<<<");
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            out.write(message + "\n");
            out.flush();
        }
        catch(IOException e) {
            System.out.println("Socket closed: " + e.getMessage());
        }
    }

    public void broadcast(Event event) {

        broadcast(event.toString());
    }

    public void broadcast(String message) {

        for(Socket s : clientConnections) {

            if(s == null) {

                continue;
            }

            sendMessage(s, message);
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
                System.out.println("moving");

                synchronized(gameObjectMap) {
                    ServerEventHandler.handleObjectMoveEvent(eventInfo, this);
                }
                break;
        }

    }

    private void createGameObjects() {

        synchronized(gameObjectMap) {

            for(GameObject go : gameObjectMap.values()) {
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
                case Constants.PLAYER_CHAR:
                case Constants.WALL_CHAR:
                    spawnObject(GameObjectType.byChar(objectChar), id, x, y);
                    break;

                default:
                    return;
            }

            id++;
        }
    }

    public void spawnObject(GameObjectType gameObjectType, int id, int x, int y) {

        synchronized(gameObjectMap) {

            gameObjectMap.put(id, GameObjectFactory.byType(id, gameObjectType, x, y));
            broadcast(new ObjectSpawnEvent(gameObjectType, id, x, y));
        }
    }

    public void removeObject(int id) {

        synchronized(gameObjectMap) {

            gameObjectMap.remove(id);
            broadcast(new ObjectDestroyEvent(id));
        }
    }

    public boolean allowMorePlayers() {

        for(Socket s : clientConnections) {

            if(s == null) {

                return true;
            }
        }
        return false;
    }
}