package org.miobook.models;

public class BuyItem extends PurchaseItem {
    public BuyItem(Book book) {
        super(book);
        this.price = book.getPrice();
    }
}
