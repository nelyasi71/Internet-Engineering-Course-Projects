package org.miobook.models;

import lombok.Getter;

import java.time.LocalDateTime;


public class Review {

    private Customer customer;
    private String comment;
    
    @Getter
    private int score;
    LocalDateTime date;

}
