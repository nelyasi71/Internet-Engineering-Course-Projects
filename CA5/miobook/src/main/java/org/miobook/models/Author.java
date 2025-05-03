package org.miobook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

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