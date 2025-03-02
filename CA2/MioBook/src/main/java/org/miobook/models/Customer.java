package org.miobook.models;

import java.util.List;

public class Customer extends User {

    private Cart shoppingCart;
    private List<Purchase> purchasesHistory;
    
    //TODO
    private List<PurchaseItem> purchasedBooks;

    public Customer(String userName, String password, String email, Address address) {
        super(userName, password, email, address);

        this.shoppingCart = new Cart();
    }

    public void addCart(PurchaseItem item) {
        this.shoppingCart.add(item);
    }
}
