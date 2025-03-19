package org.miobook.repositories;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.responses.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
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

    public void add(AddUser dto) {
        if(dto.getRole().equals("customer")) {
            customers.add(new Customer(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getAddress()));
        } else {
            admins.add(new Admin(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getAddress()));
        }
    }

}
