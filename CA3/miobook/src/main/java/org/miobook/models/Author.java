package org.miobook.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class Author {
    private String name;
    private String penName;
    private String nationality;
    private LocalDate born;
    private LocalDate death;

    public Author(String name, String penName, String nationality, LocalDate born, LocalDate death) {
        this.name = name;
        this.penName = penName;
        this.nationality = nationality;
        this.born = born;
        this.death = death;
    }
}