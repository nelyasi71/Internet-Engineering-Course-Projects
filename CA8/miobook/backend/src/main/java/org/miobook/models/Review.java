package org.miobook.models;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Book book;

    private String comment;
    private Integer rate;
    LocalDateTime date;

    public Review(Customer customer, String comment, Integer rate, LocalDateTime date) {
        this.customer = customer;
        this.comment = comment;
        this.rate = rate;
        this.date = date;
    }
}
