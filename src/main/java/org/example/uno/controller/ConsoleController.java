package org.example.uno.controller;

import java.util.Scanner;

public class ConsoleController {
    private Scanner scanner;

    public ConsoleController() {
        scanner = new Scanner(System.in);
    }

    public String getCommand() {
        System.out.print("Enter Command > ");
        return scanner.nextLine().trim();
    }
}