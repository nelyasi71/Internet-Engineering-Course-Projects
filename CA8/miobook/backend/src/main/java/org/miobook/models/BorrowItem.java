package org.miobook.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import org.miobook.responses.PurchaseItemRecord;

import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("BORROW")
@NoArgsConstructor
public class BorrowItem extends PurchaseItem {

    private int borrowDays;

    public BorrowItem(Book book, int borrowDays) {
        super(book);

        this.borrowDays = borrowDays;
        this.price = (int) book.getPrice() * this.borrowDays / 10;
    }

    public BorrowItem(BorrowItem other) {
        super(other.book);

        this.borrowDays = other.borrowDays;
        this.price = (int) this.book.getPrice() * this.borrowDays / 10;
    }

    public boolean isValid(LocalDateTime borrowDate) {
        return borrowDate.plusDays(borrowDays).isAfter(LocalDateTime.now());
    }

    public LocalDateTime until(LocalDateTime borrowDate) {
        return borrowDate.plusDays(borrowDays);
    }

    public PurchaseItemRecord createRecord() {
        return new PurchaseItemRecord(
                this.book.getTitle(),
                this.book.getAuthor().getName(),
                this.book.getPublisher(),
                this.book.getGenres(),
                this.book.getPublishedYear(),
                this.book.getPrice(),
                true,
                this.price,
                this.borrowDays
        );
    }
}
