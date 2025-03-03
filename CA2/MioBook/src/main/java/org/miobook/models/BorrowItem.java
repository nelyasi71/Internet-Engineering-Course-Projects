package org.miobook.models;

import org.miobook.responses.CartItemRecord;

import java.time.LocalDateTime;

public class BorrowItem extends PurchaseItem {

    private int borrowDays;

    public BorrowItem(Book book, int borrowDays) {
        super(book);

        this.borrowDays = borrowDays;
        this.price = (int) book.getPrice() * this.borrowDays / 10;
    }

    public boolean isValid(LocalDateTime borrowDate) {
        return borrowDate.plusDays(borrowDays).isBefore(LocalDateTime.now());
    }

    public CartItemRecord createRecord() {
        return new CartItemRecord(
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
