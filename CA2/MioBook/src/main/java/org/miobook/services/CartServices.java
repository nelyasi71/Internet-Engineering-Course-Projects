package org.miobook.services;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.Repositories;
import org.miobook.responses.CartRecord;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.responses.PurchaseItemRecord;

import java.util.List;
import java.util.Optional;

public class CartServices {
    public static void addCart(AddCart dto) throws IllegalArgumentException {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot add items to the cart. Only customers can.");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not exist.");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }

        customer.get().addCart(new BuyItem(book.get()));
    }


    public static void removeCart(RemoveCart dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot remove items from the cart. Only customers can.");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not exist.");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found in the catalog.");
        }

        customer.get().removeCard(dto.getTitle());
    }

    public static PurchaseCartRecord purchaseCart(PurchaseCart dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '\" + dto.getUsername() + \"' cannot make a purchase. Only customers can.");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '\" + dto.getUsername() + \"' not found.");
        }

        Purchase purchase = customer.get().purchaseCart();
        return new PurchaseCartRecord(
                purchase.size(), purchase.getPrice(), purchase.getDate()
        );

    }

    public static void borrowBook(BorrowBook dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '\" + dto.getUsername() + \"' cannot borrow books. Only customers can.");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '\" + dto.getUsername() + \"' not found.");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '\" + dto.getTitle() + \"' not found.");
        }

        customer.get().addCart(new BorrowItem(book.get(), dto.getDays()));
    }

    public static CartRecord showCart(ShowCart dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot view carts. Only customers can.");
        }

        Optional<Customer> _customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(_customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        Customer customer = _customer.get();
        Cart cart = _customer.get().getShoppingCart();
        List<PurchaseItemRecord> purchaseItemRecords = cart.getItems().stream()
                .map(PurchaseItem::createRecord)
                .toList();

        return new CartRecord(customer.getUsername(), cart.price(), purchaseItemRecords);
    }
}
