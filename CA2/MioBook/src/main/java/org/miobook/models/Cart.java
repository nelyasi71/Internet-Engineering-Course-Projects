package org.miobook.models;

import lombok.Getter;

import java.util.List;

public class Cart {
    private List<PurchaseItem> items;



    public void add(PurchaseItem item) {
        if(items.size() > 10) {
            throw new IllegalArgumentException("not aaa");
        }
        items.add(item);
    }
}
