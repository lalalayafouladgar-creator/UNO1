package org.example.uno.net;

import org.example.uno.controller.GameController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
    private ServerSocket serverSocket;
    private GameController gameController;
    private ArrayList<ClientHandler> clients;

    public GameServer(int port, GameController gameController) {
        this.gameController = gameController;
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
        } catch (IOException e) {
            System.out.println("Could not start server: " + e.getMessage());
        }
    }

    public void start() {
        if (serverSocket == null) {
            return;
        }
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("A client connected!");
                ClientHandler client = new ClientHandler(socket, gameController, clients);
                clients.add(client);
                Thread thread = new Thread(client);
                thread.start();
            } catch (IOException e) {
                System.out.println("Connection error: " + e.getMessage());
            }
        }
    }
}
