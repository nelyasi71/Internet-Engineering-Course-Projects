package org.miobook.repositories;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.responses.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserRepository {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Admin> admins = new ArrayList<>();

    public boolean doesCustomerExist(String username) {
        return customers.stream()
                .anyMatch(customer -> customer.getUsername().equals(username));
    }

    public boolean doesAdminExist(String username) {
        return admins.stream()
                .anyMatch(admin -> admin.getUsername().equals(username));
    }

    public boolean doesUserExist(String username) {
        if(admins.stream().noneMatch(admin -> admin.getUsername().equals(username))) {
            return customers.stream()
                    .anyMatch(customer -> customer.getUsername().equals(username));
        } else {
            return true;
        }
    }

    public boolean doesEmailExist(String email) {
        return Stream.concat(customers.stream(), admins.stream())
                .anyMatch(user -> user.getEmail().equals(email));
    }

    public boolean isCreditValid(int credit) {
        return credit > 0;
    }

    public void addUser(AddUser dto) {
        if(doesUserExist(dto.getUsername())) {
            throw new IllegalArgumentException("User with the username '" + dto.getUsername() + "' already exists.");
        }
        if(doesEmailExist(dto.getEmail())) {
            throw new IllegalArgumentException("An account with the email '" + dto.getEmail() + "' already exists.");
        }

        if(dto.getRole().equals("customer")) {
            customers.add(new Customer(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getAddress()));
        } else {
            admins.add(new Admin(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getAddress()));
        }

    }

    public void addCredit(AddCredit dto) {
        Optional<Customer> customerOptional = customers.stream()
        .filter(customer -> customer.getUsername().equals(dto.getUsername()))
                .findFirst();

        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }
        if (!isCreditValid(dto.getCredit())){
            throw new IllegalArgumentException("Invalid credit amount: \" + dto.getCredit() + \". Please enter a positive value.");
        }
        Customer customer = customerOptional.get();
        customer.addCredit(dto.getCredit());
    }

    public void addCart(AddCart dto) throws IllegalArgumentException {
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


    public void removeCart(RemoveCart dto) {
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

    public PurchaseCartRecord purchaseCart(PurchaseCart dto) {
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

    public void borrowBook(BorrowBook dto) {
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

    public UserRecord showUserDetails(ShowUserDetails dto) {
        Optional<User> user = Repositories.userRepository.getUserByUsername(dto.getUsername());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User with username '" + dto.getUsername() + "' not found.");
        }
        if(user.get() instanceof Customer customer) {
            return new UserRecord(customer.getUsername(), "customer", customer.getEmail(), customer.getAddress(), customer.getBalance());
        } else {
            Admin admin = (Admin) user.get();
            return new UserRecord(admin.getUsername(), "admin", admin.getEmail(), admin.getAddress(), null);
        }

    }

    public CartRecord showCart(ShowCart dto) {
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

    public PurchaseHistoryRecord showPurchaseHistory(ShowPurchaseHistory dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> _customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(_customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Customer customer = _customer.get();
        List<Purchase> purchases = _customer.get().getPurchasesHistory();
        List<PurchaseRecord> purchaseRecords = purchases.stream()
                .map(Purchase::createRecord)
                .toList();


        return new PurchaseHistoryRecord(customer.getUsername(), purchaseRecords);
    }
    public PurchasedBooksRecord showPurchasedBooks(ShowPurchasedBooks dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        return customer.get().createPurchasedBooksRecord();
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customers.stream()
                .filter(customer -> customer.getUsername().equals(username))
                .findFirst();
    }
    public Optional<User> getUserByUsername(String username) {
        return Stream.concat(customers.stream(), admins.stream())
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
}
