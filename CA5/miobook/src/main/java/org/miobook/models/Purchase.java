package org.miobook.models;

import lombok.Getter;
import lombok.ToString;
import org.miobook.responses.PurchaseItemRecord;
import org.miobook.responses.PurchaseRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@ToString
public class Purchase {
    private List<PurchaseItem> purchaseItems;

    private int price;

    private LocalDateTime date;


    public Purchase(List<PurchaseItem> purchaseItems, int price) {
        this.purchaseItems = purchaseItems.stream()
                .map(purchaseItem -> {
                    if (purchaseItem instanceof BorrowItem borrowItem)
                        return new BorrowItem(borrowItem);
                    else if (purchaseItem instanceof BuyItem buyItem)
                        return new BuyItem(buyItem);
                    return null;
                })
                .collect(Collectors.toList());
        date = LocalDateTime.now();
        this.price = price;
        this.purchaseItems
                .forEach(purchaseItem -> {
                    purchaseItem.getBook().newBuy();
                });
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

    public PurchaseRecord createRecord() {
        List<PurchaseItemRecord> purchaseItemRecords = purchaseItems.stream()
                .map(PurchaseItem::createRecord)
                .toList();
        return new PurchaseRecord(this.date, purchaseItemRecords, this.price);
    }

}
