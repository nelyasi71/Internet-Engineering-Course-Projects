package org.miobook.repositories;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.responses.PurchaseCartRecord;
import org.miobook.responses.UserRecord;

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
            throw new IllegalArgumentException("not aaa");
        }
        if(doesEmailExist(dto.getEmail())) {
            throw new IllegalArgumentException("not aaa");
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
            throw new IllegalArgumentException("not aaa");
        }
        if (!isCreditValid(dto.getCredit())){
            throw new IllegalArgumentException("not aaa");
        }
        Customer customer = customerOptional.get();
        customer.addCredit(dto.getCredit());
    }

    public void addCart(AddCart dto) throws IllegalArgumentException {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        customer.get().addCart(new BuyItem(book.get()));
    }


    public void removeCart(RemoveCart dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        PurchaseItem item = new PurchaseItem(book.get());
        customer.get().removeCard(item);
    }

    public PurchaseCartRecord purchaseCart(PurchaseCart dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Purchase purchase = customer.get().purchaseCart();
        return new PurchaseCartRecord(
                purchase.size(), purchase.getPrice(), purchase.getDate()
        );

    }

    public void borrowBook(BorrowBook dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Book> book = Repositories.bookRepository.getBookByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        customer.get().addCart(new BorrowItem(book.get(), dto.getDays()));

    }

    public UserRecord showUserDetails(ShowUserDetails dto) {
        Optional<User> user = Repositories.userRepository.getUserByUsername(dto.getUsername());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        if(user.get() instanceof Customer customer) {
            return new UserRecord(customer.getUsername(), "customer", customer.getEmail(), customer.getAddress(), customer.getBalance());
        } else {
            Admin admin = (Admin) user.get();
            return new UserRecord(admin.getUsername(), "admin", admin.getEmail(), admin.getAddress(), null);
        }

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
