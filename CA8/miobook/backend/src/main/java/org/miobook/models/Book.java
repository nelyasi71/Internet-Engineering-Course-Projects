package org.miobook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> genres = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "admin_id")  
    private Admin addedBy;

    private String title;
    private String publisher;
    private int publishedYear;
    private int price;
    private String synopsis;
    private String content;
    private int totalBuys;

    public Book(String title, Author author, String publisher, int publishedYear, List<String> genres, int price, String content, String synopsis, Admin addedBy) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishedYear = publishedYear;
        this.genres = genres;
        this.price = price;
        this.synopsis = synopsis;
        this.content = content;
        this.reviews = new ArrayList<>();
        this.totalBuys = 0;
        this.addedBy = addedBy;
    }

    public void addReview(Review review) {
        reviews.stream()
                .filter(r -> r.getCustomer().equals(review.getCustomer()))
                .findFirst()
                .ifPresentOrElse(
                        existingReview -> updateReview(existingReview, review),
                        () -> reviews.add(review)
                );
    }

    private void updateReview(Review existingReview, Review newReview) {
        existingReview.setComment(newReview.getComment());
        existingReview.setRate(newReview.getRate());
        existingReview.setDate(LocalDateTime.now());
    }

    public double averageRating() {
        return this.reviews.stream()
                .mapToDouble(Review::getRate)
                .average()
                .orElse(0);
    }

    public int ReviewCount() {
        return reviews.size();
    }

    public void newBuy() {
        this.totalBuys += 1;
    }
}