package org.miobook.models;

import lombok.Getter;

import java.util.List;
import java.util.Optional;


public class Cart {

    @Getter
    private List<PurchaseItem> items;

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public int getPrice() {
        return items.stream()
                .mapToInt(item -> item.getBook().getPrice())
                .sum();
    }

    public void add(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItemByBookTitle(purchaseItem.getBook().getTitle());
        if(item.isPresent()) {
            throw new IllegalArgumentException("not aaa");
        }
        if(items.size() > 10) {
            throw new IllegalArgumentException("not aaa");
        }
        items.add(purchaseItem);
    }

    public void remove(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItemByBookTitle(purchaseItem.getBook().getTitle());
        if(item.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        items.remove(item.get());
    }

    public Optional<PurchaseItem> getItemByBookTitle(String bookTitle) {
        return items.stream()
                .filter(item -> item.getBook().getTitle().equals(bookTitle))
                .findFirst();
    }
}
