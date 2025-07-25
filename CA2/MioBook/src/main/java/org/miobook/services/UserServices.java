package org.miobook.services;

import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.Repositories;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.*;

import java.util.List;
import java.util.Optional;

public class UserServices {

    public static void addUser(AddUser dto) {
        if(Repositories.userRepository.doesUserExist(dto.getUsername())) {
            throw new IllegalArgumentException("User with the username '" + dto.getUsername() + "' already exists.");
        }
        if(Repositories.userRepository.doesEmailExist(dto.getEmail())) {
            throw new IllegalArgumentException("An account with the email '" + dto.getEmail() + "' already exists.");
        }

        Repositories.userRepository.add(dto);
    }

    public static void addCredit(AddCredit dto) {

        Optional<Customer> customerOptional = Repositories.userRepository.getCustomerByUsername(dto.getUsername());

        if(customerOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer with username '" + dto.getUsername() + "' not found.");
        }

        customerOptional.get().addCredit(dto.getCredit());
    }



    public static UserRecord showUserDetails(ShowUserDetails dto) {
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


    public static PurchaseHistoryRecord showPurchaseHistory(ShowPurchaseHistory dto) {
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
    public static PurchasedBooksRecord showPurchasedBooks(ShowPurchasedBooks dto) {
        if(Repositories.userRepository.doesAdminExist(dto.getUsername())) {
            throw new IllegalArgumentException("not aaa");
        }

        Optional<Customer> customer = Repositories.userRepository.getCustomerByUsername(dto.getUsername());
        if(customer.isEmpty()) {
            throw new IllegalArgumentException("not aaa");
        }

        return customer.get().createPurchasedBooksRecord();
    }
}
