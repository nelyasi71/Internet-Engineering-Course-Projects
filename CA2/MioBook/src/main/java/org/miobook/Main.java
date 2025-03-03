package org.miobook;

import org.miobook.cli.CommandProcessor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CommandProcessor cm = new CommandProcessor();
//        Scanner scanner = new Scanner(System.in);
        List<String> commands = new ArrayList<>();
        commands.add("add_user {\"role\": \"customer\", \"username\": \"user\", \"password\": \"1234\", \"email\": \"my.mail@mail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Karaj\"}}");
        commands.add("add_user {\"role\": \"admin\", \"username\": \"admin\", \"password\": \"1234\", \"email\": \"my.mail2@mail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Karaj\"}}");
        commands.add("add_author {\"username\": \"admin\", \"nationality\": \"IRAN\", \"name\": \"author\", \"penName\": \"abc\", \"born\": \"1982-04-12\"}");
        commands.add("add_book {\"username\": \"admin\", \"title\": \"book1\", \"author\": \"author\", \"publisher\": \"name\", \"year\": 2012, \"price\": 250, \"synopsis\": \"lorem\", \"content\": \"lorem ipsum\", \"genres\": [\"horror\", \"thriller\"]}");
        commands.add("add_book {\"username\": \"admin\", \"title\": \"book2\", \"author\": \"author\", \"publisher\": \"name\", \"year\": 2012, \"price\": 250, \"synopsis\": \"lorem\", \"content\": \"lorem ipsum\", \"genres\": [\"horror\", \"thriller\"]}");
        commands.add("add_book {\"username\": \"admin\", \"title\": \"tehran\", \"author\": \"author\", \"publisher\": \"name\", \"year\": 2012, \"price\": 250, \"synopsis\": \"lorem\", \"content\": \"lorem ipsum\", \"genres\": [\"horror\", \"thriller\"]}");
//        commands.add("add_cart {\"username\": \"user\", \"title\": \"book\"}");
        commands.add("borrow_book {\"username\": \"user\", \"title\": \"book\", \"days\": 1}");
        commands.add("add_credit {\"username\": \"user\", \"credit\": 8200}");
//        commands.add("purchase_cart {\"username\": \"user\"}");
//        commands.add("show_user_details {\"username\": \"user\"}");
//        commands.add("show_user_details {\"username\": \"admin\"}");
//        commands.add("show_author_details {\"username\": \"author\"}");
//        commands.add("show_book_details {\"title\": \"book\"}");
//        commands.add("show_book_content {\"username\": \"user\", \"title\": \"book\"}");
        commands.add("show_cart {\"username\": \"user\"}");
        commands.add("purchase_cart {\"username\": \"user\"}");
        commands.add("show_cart {\"username\": \"user\"}");
        commands.add("add_review {\"username\": \"user\", \"title\": \"book1\", \"rate\": 4, \"comment\": \"This is the perfect book\"}");
        commands.add("add_review {\"username\": \"user\", \"title\": \"book1\", \"rate\": 1, \"comment\": \"This is the worst book ever\"}");
        commands.add("search_books_by_title {\"title\": \"book\"}");
        commands.add("show_book_reviews {\"title\": \"book1\"}");
        for (String command : commands) {
//        while (true) {
//            System.out.print("Enter command (or 'exit' to quit): ");
//            String input = scanner.nextLine().trim();
//
//            if ("exit".equalsIgnoreCase(input)) {
//                System.out.println("Exiting...");
//                break;
//            }

            cm.processCommand(command);
        }

//        scanner.close();

    }
}