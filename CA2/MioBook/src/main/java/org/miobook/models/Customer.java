package org.miobook.models;

import java.util.List;
import java.util.Optional;

public class Customer extends User {

    private final Cart shoppingCart;
    private List<Purchase> purchasesHistory;
    private Wallet wallet;

    //TODO
    private List<PurchaseItem> purchasedBooks;

    public Customer(String userName, String password, String email, Address address) {
        super(userName, password, email, address);

        this.shoppingCart = new Cart();
        this.wallet = new Wallet();
    }

    public void addCart(PurchaseItem purchaseItem) {
        this.shoppingCart.add(purchaseItem);
    }

    public void removeCard(PurchaseItem purchaseItem) {
        this.shoppingCart.remove(purchaseItem);
    }

    public void purchaseCart() {
        if(shoppingCart.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        int cartPrice = shoppingCart.getPrice();
        if(this.wallet.getCredit() < cartPrice) {
            throw new IllegalArgumentException("not aaa");
        }

        shoppingCart.clear();
        wallet.decreaseCredit(cartPrice);
        Purchase newPurchase = new Purchase(shoppingCart.getItems());
        purchasesHistory.add(newPurchase);
    }

    public void addCredit(int credit) throws IllegalArgumentException {
        wallet.addCredit(credit);
    }
}
