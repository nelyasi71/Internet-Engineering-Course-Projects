package org.miobook.models;


import lombok.Getter;
import org.miobook.responses.PurchaseItemRecord;

@Getter
public abstract class PurchaseItem {
    protected Book book;
    int price;

    public PurchaseItem(Book book) {
        this.book = book;
    }

    public PurchaseItemRecord createRecord() {
        return null;
    }
}
