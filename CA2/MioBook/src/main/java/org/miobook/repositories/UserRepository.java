package org.miobook.repositories;

import org.miobook.commands.AddUser;
import org.miobook.models.Admin;
import org.miobook.models.Customer;

import java.util.ArrayList;
import java.util.List;
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

//    public Optional<User> getUserByUsername(String username) {
//        return users.stream()
//                .filter(user -> user.getUserName().equals(username))
//                .findFirst();
//    }

}
