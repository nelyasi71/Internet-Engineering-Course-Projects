package org.miobook.services;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.AuthorRepository;
import org.miobook.repositories.BookRepository;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.CartRecord;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.responses.PurchaseItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class CartServices implements Services {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    public void addCart(AddCart dto) throws IllegalArgumentException {
        if(userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot add items to the cart. Only customers can.");
        }

        Optional<Customer> customerOptional = userRepository.getCustomerByUsername(dto.getUsername());
        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not exist.");
        }

        Customer customer = customerOptional.get();

        Optional<Book> book = bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found.");
        }

        if (customer.hasBook(dto.getTitle())) {
            throw new IllegalArgumentException("Cannot add this item to the cart.You have bought it before");
        }
        customer.addCart(new BuyItem(book.get()));
    }


    public void removeCart(RemoveCart dto) {
        if(userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot remove items from the cart. Only customers can.");
        }

        Optional<Customer> customer = userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' does not exist.");
        }

        Optional<Book> book = bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '" + dto.getTitle() + "' not found in the catalog.");
        }

        customer.get().removeCard(dto.getTitle());
    }

    public PurchaseCartRecord purchaseCart(PurchaseCart dto) {
        if(userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '\" + dto.getUsername() + \"' cannot make a purchase. Only customers can.");
        }

        Optional<Customer> customer = userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '\" + dto.getUsername() + \"' not found.");
        }

        Purchase purchase = customer.get().purchaseCart();
        return new PurchaseCartRecord(
                purchase.size(), purchase.getPrice(), purchase.getDate()
        );

    }

    public void borrowBook(BorrowBook dto) {
        if(userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '\" + dto.getUsername() + \"' cannot borrow books. Only customers can.");
        }

        Optional<Customer> customer = userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '\" + dto.getUsername() + \"' not found.");
        }

        Optional<Book> book = bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("Book with title '\" + dto.getTitle() + \"' not found.");
        }

        customer.get().addCart(new BorrowItem(book.get(), dto.getDays()));
    }

    public CartRecord showCart(ShowCart dto) {
        if(userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("Admin with username '" + dto.getUsername() + "' cannot view carts. Only customers can.");
        }

        Optional<Customer> _customer = userRepository.getCustomerByUsername(dto.getUsername());
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
