package org.miobook.repositories;

import org.miobook.commands.AddCart;
import org.miobook.commands.AddCredit;
import org.miobook.commands.AddUser;
import org.miobook.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class UserRepository {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Admin> admins = new ArrayList<>();

    public boolean doesCustomerExist(String username) {
        return customers.stream()
                .anyMatch(customer -> customer.getUserName().equals(username));
    }

    public boolean doesAdminExist(String username) {
        return admins.stream()
                .anyMatch(admin -> admin.getUserName().equals(username));
    }

    public boolean doesUserExist(String username) {
        if(admins.stream().noneMatch(admin -> admin.getUserName().equals(username))) {
            return customers.stream()
                    .anyMatch(customer -> customer.getUserName().equals(username));
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

        System.out.println("aaa");
    }

    public void addCredit(AddCredit dto) {
        Optional<Customer> customerOptional = customers.stream()
        .filter(customer -> customer.getUserName().equals(dto.getUsername()))
                .findFirst();

        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }
        if (!isCreditValid(dto.getCredit())){
            throw new IllegalArgumentException("not aaa");
        }
        Customer customer = customerOptional.get();
        customer.getWallet().setCredit(customer.getWallet().getCredit() + dto.getCredit());
    }

//    public Optional<User> getUserByUsername(String username) {
//        return users.stream()
//                .filter(user -> user.getUserName().equals(username))
//                .findFirst();
//    }
    public void addCart(AddCart dto) {
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

        PurchaseItem item = new BuyItem(book.get());
        customer.get().addCart(item);
    }

    public Optional<Customer> getCustomerByUsername(String username) {
        return customers.stream()
                .filter(customer -> customer.getUserName().equals(username))
                .findFirst();
    }
}
