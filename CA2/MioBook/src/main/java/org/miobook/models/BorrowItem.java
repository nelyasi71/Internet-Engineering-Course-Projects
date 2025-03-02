package org.miobook.models;

import java.time.LocalDateTime;

public class BorrowItem extends PurchaseItem {

    private int borrowDays;

    public BorrowItem(Book book, int borrowDays) {
        super(book);

        this.borrowDays = borrowDays;
    }
}
