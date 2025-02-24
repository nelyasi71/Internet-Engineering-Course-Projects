package org.miobook.models;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Author {
    private String userName;
    private String name;
    private String penName;
    private LocalDate born;
    private LocalDate death;

    public Author(String userName, String name, String penName, LocalDate born, LocalDate death) {
        this.userName = userName;
        this.name = name;
        this.penName = penName;
        this.born = born;
        this.death = death;
    }
}