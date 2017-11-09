package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.events.EventType;
import org.academiadecodigo.bomberwoman.gameObjects.GameObject;
import org.academiadecodigo.bomberwoman.threads.network.ClientEventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

/**
 * Created by codecadet on 06/11/17.
 */
public class NetworkThread implements Runnable {

    private Socket clientSocket;

    private BufferedReader clientReader;

    private PrintWriter clientWriter;

    private String ipAddress;

    private Game game;

    public NetworkThread(String ipAddress, Game game) {

        this.game = game;
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

        System.out.println(1);
        if (!Event.isEvent(eventPacket)) {

            System.out.println("Invalid event");
            return;
        }

        String[] eventInfo = eventPacket.split(Event.SEPARATOR);

        System.out.println(2);


        EventType eType = EventType.values()[Integer.parseInt(eventInfo[1])];

        switch (eType) {

            case OBJECT_SPAWN:
                ClientEventHandler.handleObjectSpawnEvent(eventInfo, game);
                break;

            case OBJECT_MOVE:
                System.out.println(3);
                ClientEventHandler.handleObjectMoveEvent(eventInfo, game);
                break;
        }

    }

}
