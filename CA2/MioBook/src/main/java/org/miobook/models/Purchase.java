package org.miobook.models;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
public class Purchase {
    private List<PurchaseItem> purchaseItems;

    @Getter
    private int price;

    @Getter
    private LocalDateTime date;


    public Purchase(List<PurchaseItem> purchaseItems, int price) {
        this.purchaseItems = purchaseItems.stream()
                .map(PurchaseItem::new)
                .collect(Collectors.toList());
        date = LocalDateTime.now();
        this.price = price;
    }

    public int size() {
        return purchaseItems.size();
    }

    public boolean hasBook(String title) {
        return purchaseItems.stream()
                .filter(item -> item.getBook().getTitle().equals(title))
                .anyMatch(item -> {
                    if (item instanceof BorrowItem borrowItem) {
                        return borrowItem.isValid(this.date);
                    } else  {
                        return true;
                    }
                });
    }

}
