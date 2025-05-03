package org.miobook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.miobook.Exception.MioBookException;
import org.miobook.responses.PurchasedBookItemRecord;
import org.miobook.responses.PurchasedBooksRecord;

import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("customer")
@Getter
@NoArgsConstructor
public class Customer extends User {

    @OneToOne(cascade = CascadeType.ALL)
    private final Cart cart = new Cart();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private final List<Purchase> purchasesHistory = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private final Wallet wallet = new Wallet();

    public Customer(String userName, String password, String email, Address address) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    @Override
    public String getRole() {
        return "customer";
    }

    public void addCart(PurchaseItem purchaseItem) {
        if(hasBook(purchaseItem.getBook().getTitle())) {
            throw new MioBookException("You already has bought this item.");
        }
        this.cart.add(purchaseItem);
    }

    public void removeCart(String title) {
        this.cart.remove(title);
    }

    public Purchase purchaseCart() {
        if(cart.isEmpty()) {
            throw new MioBookException("Cannot proceed with purchase: Shopping cart is empty.");
        }

        int cartPrice = cart.price();
        if(this.wallet.getCredit() < cartPrice) {
            throw new MioBookException("Insufficient funds: Wallet credit is lower than the total cart price.");
        }


        Purchase newPurchase = new Purchase(cart.getItems(), cart.price());
        wallet.decreaseCredit(cartPrice);
        cart.clear();
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
                            (purchaseItem instanceof BorrowItem),
                            (purchaseItem instanceof BorrowItem) ? ((BorrowItem) purchaseItem).until(purchase.getDate()) : null
                    );
                }))
                .toList();
        return new PurchasedBooksRecord(this.username, purchasedBookItemRecords);
    }
}
