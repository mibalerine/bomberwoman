package org.academiadecodigo.bomberwoman.threads;

import org.academiadecodigo.bomberwoman.Constants;
import org.academiadecodigo.bomberwoman.Game;
import org.academiadecodigo.bomberwoman.Utils;
import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.events.EventType;
import org.academiadecodigo.bomberwoman.threads.network.ClientEventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

        establishConnection();

        start();
    }

    private void establishConnection() {

        try {

            clientSocket = new Socket(ipAddress, Constants.PORT);
            clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientWriter = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public void sendEvent(Event event) {
        sendMessage(event.toString());
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
                    break;
                }

                handleEvent(line);
            } catch (IOException e) {

                try {

                    clientReader.close();
                    clientReader = null;

                } catch (IOException b) {
                }

            }
        }

        try {
            clientSocket.close();
            clientReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEvent(String eventPacket) {

        if (!Event.isEvent(eventPacket)) {
            return;
        }

        String[] eventInfo = eventPacket.split(Event.SEPARATOR);

        EventType eType = EventType.values()[Integer.parseInt(eventInfo[1])];

        switch (eType) {

            case LEVEL_START:
                ClientEventHandler.handleLevelStartEvent();
                break;

            case REFRESH_SCREEN:
                Game.getInstance().refreshRenderThread();
                break;

            case OBJECT_SPAWN:
                ClientEventHandler.handleObjectSpawnEvent(eventInfo, game);
                break;

            case OBJECT_MOVE:
                ClientEventHandler.handleObjectMoveEvent(eventInfo, game);
                break;

            case OBJECT_DESTROY:
                ClientEventHandler.handleObjectDestroyEvent(eventInfo, game);
                break;

            case PLAYER_ID:
                ClientEventHandler.handlePlayerAssignEvent(eventInfo, game);
                break;

            case SERVER_CLOSE:
                System.out.println();
                Game.getInstance().closeClient();
                break;
        }
    }

    public void setIpAddress(String ipAddress) {

        this.ipAddress = ipAddress;
    }

    public void close() {
        try {
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}