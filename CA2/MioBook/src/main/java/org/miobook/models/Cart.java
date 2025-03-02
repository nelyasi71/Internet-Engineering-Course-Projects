package org.miobook.models;

import lombok.Getter;

import java.util.List;
import java.util.Optional;


public class Cart {

    @Getter
    private List<PurchaseItem> items;

    public void add(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItem(purchaseItem.getBook().getTitle());
        if(item.isPresent()) {
            throw new IllegalArgumentException("not aaa");
        }
        if(items.size() > 10) {
            throw new IllegalArgumentException("not aaa");
        }
        items.add(purchaseItem);
    }

    public void remove(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItem(purchaseItem.getBook().getTitle());
        if(item.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        items.remove(item.get());
    }


    public Optional<PurchaseItem> getItem(String bookTitle) {
        return items.stream()
                .filter(item -> item.getBook().getTitle().equals(bookTitle))
                .findFirst();
    }
}
