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
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

/**
 * Created by codecadet on 06/11/17.
 */
public class NetworkThread implements Runnable {

    private final Map<Integer, GameObject> gameObjects;

    private Socket clientSocket;

    private BufferedReader clientReader;

    private PrintWriter clientWriter;

    private String ipAddress;

    public NetworkThread(Map<Integer, GameObject> gameObjects, String ipAddress) {

        this.gameObjects = gameObjects;
        this.ipAddress = ipAddress;
    }

    @Override
    public void run() {

        establishConnection(ipAddress);

        start();
    }

    public void establishConnection(String idAddress) {

        try {

            clientSocket = new Socket(idAddress, Constants.PORT);
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {

        if (clientSocket == null || clientSocket.isClosed() || clientWriter == null) {

            System.out.println("The Socket for client " + Thread.currentThread().getId() + "is closed!" + "\nRemember to call establishConnection()");
            return;
        }

        clientWriter.write(message + "\n");
        clientWriter.flush();
    }

    private void start() {

        while (!clientSocket.isClosed() && clientReader != null) {

            try {

                String line = clientReader.readLine();

                if (line == null) {

                    continue;
                }

                handleEvent(line);

            } catch (IOException e) {

                Utils.bufferedMode();
                System.out.println("I'm out bitch");
            }
        }
    }

    private void handleEvent(String eventPacket) {

        if (!Event.isEvent(eventPacket)) {

            System.out.println("Invalid event");
            return;
        }

        String[] eventInfo = eventPacket.split(Event.SEPARATOR);

        if (!Utils.isNumber(eventInfo[2]) || !Utils.isNumber(eventInfo[3]) || !Utils.isNumber(eventInfo[4]) || !Utils.isNumber(eventInfo[5])) {
            System.out.println("not a number!");
            return;
        }

        EventType eType = EventType.values()[Integer.parseInt(eventInfo[1])];

        switch (eType) {

            case OBJECT_SPAWN:
                GameObjectType goType = GameObjectType.values()[Integer.parseInt(eventInfo[2])];
                int id = Integer.parseInt(eventInfo[3]);
                int x = Integer.parseInt(eventInfo[4]);
                int y = Integer.parseInt(eventInfo[5]);
                spawnObject(goType, id, x, y);
                break;

            case OBJECT_MOVE:
                break;
        }

    }

    private void spawnObject(GameObjectType goType, int id, int x, int y) {

        GameObject gameObject = GameObjectFactory.byType(id, goType, x, y);

        synchronized (gameObjects) {

            gameObjects.put(id, gameObject);

        }

    }
}
