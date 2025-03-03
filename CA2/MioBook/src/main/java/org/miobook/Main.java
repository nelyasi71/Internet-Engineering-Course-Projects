package org.miobook;

import org.miobook.cli.CommandProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandProcessor cm = new CommandProcessor();
//        Scanner scanner = new Scanner(System.in);
        List<String> commands = new ArrayList<>();
        commands.add("add_user {\"role\": \"customer\", \"username\": \"user\", \"password\": \"1234\", \"email\": \"my.mail@mail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Karaj\"}}");
        commands.add("add_user {\"role\": \"admin\", \"username\": \"admin\", \"password\": \"1234\", \"email\": \"my.mail2@mail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Karaj\"}}");
//        commands.add("add_author {\"username\": \"admin\", \"name\": \"author\", \"penName\": \"abc\", \"born\": \"1982-04-12\"}");
//        commands.add("add_book {\"username\": \"admin\", \"title\": \"sample book\", \"author\": \"author\", \"publisher\": \"name\", \"year\": 2012, \"price\": 250, \"synopsis\": \"lorem\", \"content\": \"lorem ipsum\", \"genres\": [\"horror\", \"thriller\"]}");
//        commands.add("add_cart {\"username\": \"user\", \"title\": \"sample book\"}");
//        commands.add("add_credit {\"username\": \"user\", \"credit\": 8200}");
//        commands.add("purchase_cart {\"username\": \"user\"}");
        commands.add("show_user_details {\"username\": \"user\"}");
        commands.add("show_user_details {\"username\": \"admin\"}");
        for(int i = 0; i < commands.size(); i++) {
//        while (true) {
//            System.out.print("Enter command (or 'exit' to quit): ");
//            String input = scanner.nextLine().trim();
//
//            if ("exit".equalsIgnoreCase(input)) {
//                System.out.println("Exiting...");
//                break;
//            }

            cm.processCommand(commands.get(i));
        }

//        scanner.close();

    }
}