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
    private LocalDateTime date;

    public Purchase(List<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems.stream()
                .map(PurchaseItem::new)
                .collect(Collectors.toList());
        date = LocalDateTime.now();

    }

    public int size() {
        return purchaseItems.size();
    }

    public int price() {
        return purchaseItems.stream()
                .mapToInt(item -> item.getBook().getPrice())
                .sum();
    }
}
