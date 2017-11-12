package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.*;
import org.academiadecodigo.bomberwoman.gameObjects.*;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;
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

    private final int[][] PLAYER_SPAWN_POSITIONS = {
            {1, 1},
            {Game.WIDTH - 2, Game.HEIGHT - 2},
            {Game.WIDTH - 2, 1},
            {1, Game.HEIGHT - 2}};

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
        System.out.println("SERVER THREAD IS RUNNING");
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
                threadPool.submit(new ClientDispatcher(clientConnections[numberOfConnections], this));

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

        broadcast(new LevelStartEvent().toString());
        createGameObjects();
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

    public void broadcast(Event event) {

        broadcast(event.toString());
    }

    public void broadcast(String message) {

        for (Socket s : clientConnections) {

            if (s == null) {

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

        synchronized (gameObjectMap) {

            switch (eType) {

                case OBJECT_SPAWN:

                    id = ServerEventHandler.handleObjectSpawnEvent(eventInfo, id, this);

                    break;

                case OBJECT_MOVE:

                    ServerEventHandler.handleObjectMoveEvent(eventInfo, this);
                    break;

                case OBJECT_DESTROY:

                    //ServerEventHandler.handleObjectDestroyEvent(eventInfo, this);
                    break;
            }
        }
    }

    private void createGameObjects() {

        GameObject temp = null;
        synchronized (gameObjectMap) {

            for (GameObject go : gameObjectMap.values()) {
                temp = go;
                broadcast(new ObjectSpawnEvent(GameObjectType.PLAYER, go.getId(), go.getX(), go.getY(), false).toString());
            }
        }

        try {

            InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(ScreenHolder.LEVEL_1.getFilePath()));
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int y = 0;

            while ((line = bf.readLine()) != null) {

                char[] chars = line.toCharArray();

                for (int x = 0; x < chars.length; x++) {

                    createObject(chars[x] + "", x, y);
                }

                y++;
            }

            if (temp == null) {

                return;
            }

            broadcast(new ObjectMoveEvent(temp, Direction.STAY));
        } catch (IOException e) {

            System.out.println("Could not read file: " + e.getMessage());
        }
    }

    private void createObject(String objectChar, int x, int y) {

        synchronized (gameObjectMap) {

            switch (objectChar) {

                case Constants.BRICK_CHAR:
                case Constants.PLAYER_CHAR:
                case Constants.WALL_CHAR:
                case Constants.WALL_CHAR_BLUE:
                    spawnObject(GameObjectType.byChar(objectChar), id, x, y, false);
                    break;
                default:
                    return;
            }

            id++;
        }
    }

    public GameObject spawnObject(GameObjectType gameObjectType, int id, int x, int y, boolean shouldRefresh) {

        synchronized (gameObjectMap) {

            GameObject gameObject = GameObjectFactory.byType(id, gameObjectType, x, y);
            gameObjectMap.put(id, gameObject);
            broadcast(new ObjectSpawnEvent(gameObjectType, id, x, y, shouldRefresh));
            return gameObject;
        }
    }

    public void removeObject(int id) {

        synchronized (gameObjectMap) {

            gameObjectMap.remove(id);
            broadcast(new ObjectDestroyEvent(id));
        }
    }

    public boolean allowMorePlayers() {

        for (Socket s : clientConnections) {

            if (s == null) {

                return true;
            }
        }
        return false;
    }

    public void explode(int x, int y, int blastRadius) {

        for (Direction d : Direction.values()) {
            propagateExplosion(d, x, y, blastRadius);
        }
    }

    private void propagateExplosion(Direction dir, int x, int y, int blastRadius) {

        int horizontal = dir.getHorizontal();
        int vertical = dir.getVertical();

        for (int i = 1; i < blastRadius + 1; i++) {

            synchronized (gameObjectMap) {
                GameObject gameObject = Utils.getObjectAt(gameObjectMap.values(), x + i * horizontal, y + i * vertical);

                if (gameObject instanceof Wall) {
                    break;
                }

                if (gameObject instanceof Destroyable) {
                    removeObject(gameObject.getId());

                    if (!(gameObject instanceof Player)) {

                        break;
                    }
                }

                ServerEventHandler.setDestroyTimer(spawnObject(GameObjectType.FLAME, id++, x + i * horizontal, y + i * vertical, false), Constants.FLAME_DELAY);
            }

        }
    }

    public void closeServer() {

        threadPool.shutdown();

        for (Socket s : clientConnections) {

            try {

                s.close();

            } catch (IOException e) {
                e.getMessage();
            }
        }
    }
}