package org.miobook;

import org.miobook.cli.CommandProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandProcessor cm = new CommandProcessor();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter command (or 'exit' to quit): ");
            String input = scanner.nextLine().trim();
            if ("exit".equalsIgnoreCase(input)) {
                System.out.println("Exiting...");
                break;
            }
            cm.processCommand(input);
        }
        scanner.close();
    }
}