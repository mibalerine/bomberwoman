package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.direction.Direction;
import org.academiadecodigo.bomberwoman.events.*;
import org.academiadecodigo.bomberwoman.gameObjects.*;
import org.academiadecodigo.bomberwoman.gameObjects.control.Destroyable;
import org.academiadecodigo.bomberwoman.gameObjects.control.DoorBrick;
import org.academiadecodigo.bomberwoman.gameObjects.powerups.Powerup;
import org.academiadecodigo.bomberwoman.levels.ScreenHolder;
import org.academiadecodigo.bomberwoman.threads.server.ClientDispatcher;
import org.academiadecodigo.bomberwoman.threads.server.ServerEventHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by codecadet on 06/11/17.
 */
public class ServerThread implements Runnable {

    private final Map<Integer, GameObject> gameObjectMap;

    private final Map<Integer, GameObject> playerMap;

    private final int[][] PLAYER_SPAWN_POSITIONS = { { 1,
            1 },
            { Game.WIDTH - 2,
                    Game.HEIGHT - 5 },
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

    private ScreenHolder currentLevel;

    public ServerThread(int numberOfPlayers) {

        this.numberOfPlayers = numberOfPlayers;
        clientConnections = new Socket[numberOfPlayers];
        threadPool = Executors.newFixedThreadPool(numberOfPlayers);
        gameObjectMap = new Hashtable<>();
        playerMap = new Hashtable<>();
        id = Constants.INITIAL_ID;
        System.out.println("SERVER THREAD IS RUNNING");
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
                    GameObject player = GameObjectFactory.byType(id, GameObjectType.PLAYER, playerPosition[0], playerPosition[1]);
                    gameObjectMap.put(id, player);
                    playerMap.put(id, player);
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

        broadcast(new LevelStartEvent());

        spawnPlayers();

        loadLevel(ScreenHolder.LEVEL_1);
    }

    private void sendMessage(Socket clientSocket, String message) {

        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

            out.write(message + "\n");
            out.flush();
        }
        catch(IOException e) {
            System.out.println("Socket closed: " + e.getMessage());
        }
    }

    private void broadcast(Event event) {

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

        synchronized(gameObjectMap) {

            switch(eType) {

                case OBJECT_SPAWN:

                    id = ServerEventHandler.handleObjectSpawnEvent(eventInfo, id, this);

                    break;

                case OBJECT_MOVE:

                    ServerEventHandler.handleObjectMoveEvent(eventInfo, this);
                    break;

                case OBJECT_DESTROY:

                    //ServerEventHandler.handleObjectDestroyEvent(eventInfo, this);
                    //Client will never send this event
                    break;

                case POWERUP_PICKUP:

                    ServerEventHandler.handlePickupPowerupEvent(eventInfo, this);
                    break;
            }
        }
    }

    private void loadLevel(ScreenHolder screenHolder) {

        currentLevel = screenHolder;

        try {

            InputStream inputStream = new BufferedInputStream(getClass().getResourceAsStream(currentLevel.getFilePath()));
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int y = 0;

            while((line = bf.readLine()) != null) {

                char[] chars = line.toCharArray();

                for(int x = 0; x < chars.length; x++) {

                    createObject(chars[x] + "", x, y);
                }

                y++;
            }

            broadcast(new RefreshScreenEvent());
        }
        catch(IOException e) {

            System.out.println("Could not read file: " + e.getMessage());
        }
    }

    public void loadNextLevel() {

        gameObjectMap.clear();
        gameObjectMap.putAll(playerMap);

        id = Constants.INITIAL_ID + 5;

        broadcast(new LevelStartEvent());

        //spawnPlayers();

        int i = 0;
        for(GameObject go : playerMap.values()) {

            broadcast(new ObjectSpawnEvent(GameObjectType.PLAYER, go.getId(), PLAYER_SPAWN_POSITIONS[i][0], PLAYER_SPAWN_POSITIONS[i][1], true));
            Event event = new ObjectMoveEvent(go, PLAYER_SPAWN_POSITIONS[i][0], PLAYER_SPAWN_POSITIONS[i][1]);
            broadcast(event);

            //sendMessage(clientConnections[i], new PlayerAssignEvent(go.getId()).toString());
            i++;
        }

        //loadLevel(currentLevel.next());
    }

    private void spawnPlayers() {

        synchronized(gameObjectMap) {

            int i = 0;
            for(GameObject go : playerMap.values()) {

                broadcast(new ObjectSpawnEvent(GameObjectType.PLAYER, go.getId(), PLAYER_SPAWN_POSITIONS[i][0], PLAYER_SPAWN_POSITIONS[i][1], true));
                i++;
            }
        }
    }

    private void createObject(String objectChar, int x, int y) {

        synchronized(gameObjectMap) {

            switch(objectChar) {

                case Constants.BRICK_CHAR:
                case Constants.PLAYER_CHAR:
                case Constants.WALL_CHAR:
                case Constants.WALL_CHAR_BLUE:
                case Constants.DOOR:
                case Constants.DOOR_HIDDEN:
                    spawnObject(GameObjectType.byChar(objectChar), id, x, y, false);
                    break;
                default:
                    return;
            }

            id++;
        }
    }

    public GameObject spawnObject(GameObjectType gameObjectType, int id, int x, int y, boolean shouldRefresh) {

        synchronized(gameObjectMap) {

            GameObject gameObject = GameObjectFactory.byType(id, gameObjectType, x, y);
            gameObjectMap.put(id, gameObject);
            broadcast(new ObjectSpawnEvent(gameObjectType, id, x, y, shouldRefresh));
            return gameObject;
        }
    }

    public void removeObject(int id) {

        synchronized(gameObjectMap) {

            GameObject gameObject = gameObjectMap.get(id);
            if(gameObject != null && gameObject instanceof Brick) {

                boolean isDoor = gameObject instanceof DoorBrick;

                if(new Random().nextInt(100) < Constants.POWERUP_ODD || isDoor) {

                    spawnObject(isDoor ? GameObjectType.POWER_UP_DOOR : GameObjectType.POWER_UP, this.id++, gameObject.getX(), gameObject.getY(), true);
                }
            }

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

    public void explode(int x, int y, int blastRadius) {

        for(Direction d : Direction.values()) {
            propagateExplosion(d, x, y, blastRadius);
        }

        broadcast(new RefreshScreenEvent());
    }

    private void propagateExplosion(Direction dir, int x, int y, int blastRadius) {

        int horizontal = dir.getHorizontal();
        int vertical = dir.getVertical();

        for(int i = 1; i < blastRadius + 1; i++) {

            synchronized(gameObjectMap) {
                GameObject gameObject = Utils.getObjectAt(gameObjectMap.values(), x + i * horizontal, y + i * vertical);

                if(gameObject instanceof Wall) {
                    break;
                }

                if(gameObject instanceof Destroyable) {

                    removeObject(gameObject.getId());

                    if(!(gameObject instanceof Player) && !(gameObject instanceof Powerup)) {

                        break;
                    }
                }

                ServerEventHandler.setDestroyTimer(spawnObject(GameObjectType.FLAME, id++, x + i * horizontal, y + i * vertical, false), Constants.FLAME_DELAY);
            }
        }
    }

    public void closeServer() {

        threadPool.shutdown();

        for(Socket s : clientConnections) {

            try {

                if(s == null) {

                    continue;
                }

                s.close();
            }
            catch(IOException e) {
                e.getMessage();
            }
        }
    }

    public GameObject spawnBomb(int bombId, Player player) {

        GameObject gameObject = spawnObject(GameObjectType.BOMB, bombId, player.getX(), player.getY(), true);

        if(gameObject instanceof Bomb) {

            ((Bomb) gameObject).setBlastRadius(player.getBombRadius());
        }
        return gameObject;
    }

    public ScreenHolder getCurrentLevel() {

        return currentLevel;
    }
}