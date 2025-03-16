package org.miobook.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Book {
    private String title;
    private Author author;
    private String publisher;
    private int publishedYear;
    private List<String> genres;
    private int price;
    private String synopsis;
    private String content;
    private List<Review> reviews;

    public Book(String title, Author author, String publisher, int publishedYear, List<String> genres, int price, String content, String synopsis) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.genres = genres;
        this.price = price;
        this.synopsis = synopsis;
        this.content = content;
        this.reviews = new ArrayList<>();
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public double averageRating() {
        return this.reviews.stream()
                .mapToDouble(Review::getRate)
                .average()
                .orElse(0);
    }
}