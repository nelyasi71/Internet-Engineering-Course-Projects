package org.miobook.models;

import org.miobook.responses.CartItemRecord;

public class BuyItem extends PurchaseItem {
    public BuyItem(Book book) {
        super(book);
        this.price = book.getPrice();
    }

    public CartItemRecord createRecord() {
        return new CartItemRecord(
                this.book.getTitle(),
                this.book.getAuthor().getName(),
                this.book.getPublisher(),
                this.book.getGenres(),
                this.book.getPublishedYear(),
                this.book.getPrice(),
                false,
                this.price,
                null
        );
    }
}
