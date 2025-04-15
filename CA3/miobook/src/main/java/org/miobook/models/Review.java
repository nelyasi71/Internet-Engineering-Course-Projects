package org.miobook.models;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review {

    private Customer customer;
    private String comment;
    private Integer rate;
    private LocalDateTime date;

    public Review(Customer customer, String comment, Integer rate, LocalDateTime date) {
        this.customer = customer;
        this.comment = comment;
        this.rate = rate;
        this.date = date;
    }
}
