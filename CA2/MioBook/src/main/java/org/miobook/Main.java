package org.miobook;

import org.miobook.cli.CommandProcessor;

public class Main {
    public static void main(String[] args) {
        CommandProcessor cm = new CommandProcessor();
        cm.processCommand(
                "add_user {\"role\": \"amin\", \"username\": \"admin_user\", \"password\": \"1234\", \"email\":\n" +
                        "\"my.mail@mail.com\", \"address\": {\"country\": \"Iran\", \"city\": \"Iran\"}}"
        );
//
//          cm.processCommand(
//                "add_author {\"username\": \"admin_user\", \"name\": \"author name\", \"penName\": \"abc\",\n" +
//                        "\"born\": \"1982-04-12\"}"
//        );

//        cm.processCommand(
//        "add_book {\"username\": \"admin_user\", \"title\": \"sample book\", \"author\": \"sample" +
//                "name\", \"publisher\": \"name\", \"year\": 2012, \"price\": 250, \"synopsis\": \"lorem\",\n" +
//                "\"content\": \"lorem ipsum\", \"genres\": [\"horror\", \"thriller\"]}"
//        );
    }
}