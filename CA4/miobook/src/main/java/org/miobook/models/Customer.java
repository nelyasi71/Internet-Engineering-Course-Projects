package org.miobook.models;

import lombok.Getter;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.responses.PurchasedBookItemRecord;
import org.miobook.responses.PurchasedBooksRecord;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    @Getter
    private final Cart shoppingCart;
    @Getter
    private final List<Purchase> purchasesHistory = new ArrayList<>();
    private Wallet wallet;

    //TODO
    private final List<PurchaseItem> purchasedBooks = new ArrayList<>();

    public Customer(String userName, String password, String email, Address address) {
        super(userName, password, email, address);

        this.shoppingCart = new Cart();
        this.wallet = new Wallet();
    }

    @Override
    public String getRole() {
        return "customer";
    }

    public void addCart(PurchaseItem purchaseItem) {
        this.shoppingCart.add(purchaseItem);
    }

    public void removeCard(String title) {
        this.shoppingCart.remove(title);
    }

    public Purchase purchaseCart() {
        if(shoppingCart.isEmpty()) {
            throw new MioBookException("Cannot proceed with purchase: Shopping cart is empty.");
        }

        int cartPrice = shoppingCart.price();
        if(this.wallet.getCredit() < cartPrice) {
            throw new MioBookException("Insufficient funds: Wallet credit is lower than the total cart price.");
        }


        Purchase newPurchase = new Purchase(shoppingCart.getItems(), shoppingCart.price());
        wallet.decreaseCredit(cartPrice);
        shoppingCart.clear();
        purchasesHistory.add(newPurchase);

        return newPurchase;
    }

    public void addCredit(int credit) throws MioBookException {
        wallet.addCredit(credit);
    }

    public int getBalance() {
        return wallet.getCredit();
    }

    public boolean hasBook(String title) {
        return purchasesHistory.stream()
                .anyMatch(item -> item.hasBook(title));
    }

    public PurchasedBooksRecord createPurchasedBooksRecord() {
        List<PurchasedBookItemRecord> purchasedBookItemRecords = this.purchasesHistory.stream()
                .flatMap(purchase -> purchase.getPurchaseItems().stream()
                .filter(purchaseItem -> !(purchaseItem instanceof BorrowItem) || ((BorrowItem) purchaseItem).isValid(purchase.getDate()))
                .map(purchaseItem -> {
                    return new PurchasedBookItemRecord(
                            purchaseItem.getBook().getTitle(),
                            purchaseItem.getBook().getAuthor().getName(),
                            purchaseItem.getBook().getPublisher(),
                            purchaseItem.getBook().getGenres(),
                            purchaseItem.getBook().getPublishedYear(),
                            purchaseItem.getBook().getPrice(),
                            (purchaseItem instanceof BorrowItem)
                    );
                }))
                .toList();
        return new PurchasedBooksRecord(this.username, purchasedBookItemRecords);
    }
}
