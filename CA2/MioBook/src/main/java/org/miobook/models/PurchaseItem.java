package org.miobook.models;


import lombok.Getter;
import org.miobook.responses.CartItemRecord;

@Getter
public class PurchaseItem {
    protected Book book;
    int price;

    public PurchaseItem(Book book) {
        this.book = book;
    }

    public PurchaseItem(PurchaseItem other) {
        this.book = other.book;
        this.price = other.price;
    }

    public CartItemRecord createRecord() {
        return null;
    }
}
