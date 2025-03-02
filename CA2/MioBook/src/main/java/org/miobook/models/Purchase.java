package org.miobook.models;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {

    private List<PurchaseItem> purchaseItems;
    private LocalDateTime date;

    public Purchase(List<PurchaseItem> purchaseItems) {
        this.purchaseItems = purchaseItems;
        date = LocalDateTime.now();

    }
}
