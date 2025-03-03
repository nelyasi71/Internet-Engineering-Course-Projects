package org.miobook.models;


import lombok.Getter;

@Getter
public class PurchaseItem {
    private Book book;

    public PurchaseItem(Book book) {
        this.book = book;
    }

    public PurchaseItem(PurchaseItem other) {
        this.book = other.book;
    }

}
