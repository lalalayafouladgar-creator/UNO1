package org.example.uno.view;

import org.example.uno.controller.LoginController;
import java.util.Scanner;

public class LoginView {
    private LoginController loginController;
    private Scanner scanner;

    public LoginView(LoginController loginController, Scanner scanner) {
        this.loginController = loginController;
        this.scanner = scanner;
    }

    private boolean processCommand(String command) {
        String[] parts = command.trim().split("\\s+");

        if (parts[0].equals("register")) {
            if (parts.length != 7) {
                System.out.println("Invalid command!");
                return false;
            }
            if (!parts[1].equals("-u") || !parts[3].equals("-p") || !parts[5].equals("-n")) {
                System.out.println("Invalid command!");
                return false;
            }
            boolean result = loginController.register(parts[2], parts[4], parts[6]);
            if (result) {
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed!");
            }
            return false;
        } else if (parts[0].equals("login")) {
            if (parts.length != 5) {
                System.out.println("Invalid command!");
                return false;
            }
            if (!parts[1].equals("-u") || !parts[3].equals("-p")) {
                System.out.println("Invalid command!");
                return false;
            }
            boolean result = loginController.login(parts[2], parts[4]);
            if (result) {
                System.out.println("Login successful!");
                return true;
            } else {
                System.out.println("Login failed!");
                return false;
            }
        } else {
            System.out.println("Unknown command!");
            return false;
        }
    }

    public boolean showMenu() {
        while (true) {
            System.out.println("===== Authentication Menu =====");
            System.out.println("Enter command:");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                return false;
            }
            boolean loggedIn = processCommand(command);
            if (loggedIn) {
                return true;
            }
        }
    }
}