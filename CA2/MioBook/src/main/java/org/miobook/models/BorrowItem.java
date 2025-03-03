package org.miobook.models;

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
}
