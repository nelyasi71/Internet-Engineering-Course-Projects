package org.miobook.models;

import java.util.List;
import java.util.Optional;

public class Customer extends User {

    private final Cart shoppingCart;
    private List<Purchase> purchasesHistory;
    
    //TODO
    private List<PurchaseItem> purchasedBooks;

    public Customer(String userName, String password, String email, Address address) {
        super(userName, password, email, address);

        this.shoppingCart = new Cart();
    }

    public void addCart(PurchaseItem purchaseItem) {
        this.shoppingCart.add(purchaseItem);
    }

    public void removeCard(PurchaseItem purchaseItem) {
        this.shoppingCart.remove(purchaseItem);
    }

}
