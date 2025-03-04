package org.miobook.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Cart {

    @Getter
    private final List<PurchaseItem> items = new ArrayList<>();

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public int size() {
        return items.size();
    }

    public int price() {
        return items.stream()
                .mapToInt(PurchaseItem::getPrice)
                .sum();
    }

    public void add(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItemByBookTitle(purchaseItem.getBook().getTitle());
        if(item.isPresent()) {
            throw new IllegalArgumentException("This book is already in the purchase list.");
        }
        if(items.size() > 10) {
            throw new IllegalArgumentException("Cannot add more than 10 items to the purchase list.");
        }
        items.add(purchaseItem);
    }

    public void remove(PurchaseItem purchaseItem) {
        Optional<PurchaseItem> item = this.getItemByBookTitle(purchaseItem.getBook().getTitle());
        if(item.isEmpty()) {
            throw new IllegalArgumentException("The item you are trying to remove does not exist in the purchase list.");
        }
        items.remove(item.get());
    }

    public Optional<PurchaseItem> getItemByBookTitle(String bookTitle) {
        return items.stream()
                .filter(item -> item.getBook().getTitle().equals(bookTitle))
                .findFirst();
    }
}
