package org.academiadecodigo.bomberwoman.threads.server;

import org.academiadecodigo.bomberwoman.events.Event;
import org.academiadecodigo.bomberwoman.threads.ServerThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientDispatcher implements Runnable {

    private Socket clientConnection;

    private BufferedReader bufferedReader;

    private ServerThread serverThread;

    public ClientDispatcher(Socket clientConnection, ServerThread serverThread) {

        this.clientConnection = clientConnection;
        this.serverThread = serverThread;
    }

    @Override
    public void run() {

        while(clientConnection != null && !clientConnection.isClosed()) {

            try {

                if(bufferedReader == null) {

                    bufferedReader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                }

                String line = bufferedReader.readLine();
                if(line != null) {

                    if(!Event.isEvent(line)) {
                        continue;
                    }

                    serverThread.handleEvent(line.split(Event.SEPARATOR));
                }
            }
            catch(IOException e) {

                e.printStackTrace();
            }
        }
    }
}