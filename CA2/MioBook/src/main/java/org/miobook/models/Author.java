package org.miobook.models;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
public class Author {
    private String name;
    private String penName;
    private LocalDate born;
    private LocalDate death;

    public Author(String name, String penName, LocalDate born, LocalDate death) {
        this.name = name;
        this.penName = penName;
        this.born = born;
        this.death = death;
    }
}