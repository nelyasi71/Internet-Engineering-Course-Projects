package org.miobook.models;

import org.miobook.responses.PurchaseCartRecord;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {

    private final Cart shoppingCart;
    private final List<Purchase> purchasesHistory = new ArrayList<>();
    private Wallet wallet;

    //TODO
    private final List<PurchaseItem> purchasedBooks = new ArrayList<>();

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

    public Purchase purchaseCart() {
        if(shoppingCart.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        int cartPrice = shoppingCart.price();
        if(this.wallet.getCredit() < cartPrice) {
            throw new IllegalArgumentException("not aaa");
        }


        Purchase newPurchase = new Purchase(shoppingCart.getItems(), shoppingCart.price());
        wallet.decreaseCredit(cartPrice);
        shoppingCart.clear();
        purchasesHistory.add(newPurchase);

        System.out.println(newPurchase);

        return newPurchase;
    }

    public void addCredit(int credit) throws IllegalArgumentException {
        wallet.addCredit(credit);
    }

    public int getBalance() {
        return wallet.getCredit();
    }

    public boolean hasBook(String title) {
        return purchasesHistory.stream()
                .anyMatch(item -> item.hasBook(title));
    }
}
