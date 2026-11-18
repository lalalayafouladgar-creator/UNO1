
package org.example.uno.net;

import org.example.uno.controller.GameController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Socket socket;
    private GameController gameController;
    private ArrayList<ClientHandler> clients;
    private Scanner input;
    private PrintWriter output;

    public ClientHandler(Socket socket, GameController gameController, ArrayList<ClientHandler> clients) {
        this.socket = socket;
        this.gameController = gameController;
        this.clients = clients;
        try {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Could not create client streams.");
        }
    }

    @Override
    public void run() {
        try {
            output.println("Connected to UNO server!");
            while (input.hasNextLine()) {
                String message = input.nextLine();
                if (message.equals("exit")) {
                    break;
                }
                broadcast(message);
            }
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    private void broadcast(String message) {
        synchronized (clients) {
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).sendMessage(message);
            }
        }
    }

    private void closeConnection() {
        try {
            synchronized (clients) {
                clients.remove(this);
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            System.out.println("A client disconnected.");
        } catch (IOException e) {
            System.out.println("Error closing connection.");
        }
    }
}
