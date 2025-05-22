package org.miobook.services;

import jakarta.transaction.Transactional;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.UserRepository;
import org.miobook.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServices implements Services {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityService securityService;

    @Transactional
    public void addUser(AddUser dto) {
        if(userRepository.existsByUsername(dto.getUsername())) {
            throw new MioBookException("username", "User with the username '" + dto.getUsername() + "' already exists.");
        }
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new MioBookException("email", "An account with the email '" + dto.getEmail() + "' already exists.");
        }

        String hashedPassword = securityService.hashPassword(dto.getPassword());

        User user;
        if (dto.getRole().equals("customer")) {
            user = new Customer(dto.getUsername(), hashedPassword, dto.getEmail(), dto.getAddress());
        } else {
            user = new Admin(dto.getUsername(), hashedPassword, dto.getEmail(), dto.getAddress());
        }
        userRepository.save(user);
    }

    @Transactional
    public User getOrCreate(String email, String username) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        User user = userOpt.orElseGet(() -> {
            User newUser = new Customer();
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setPassword(null);
            return userRepository.save(newUser);
        });

        return user;
    }


    @Transactional
    public void addCredit(AddCredit dto) {
        Optional<User> customerOptional = userRepository.findByUsernameAndType(dto.getUsername(), Customer.class);
        if(customerOptional.isEmpty()) {
            throw new MioBookException("username", "Customer with username '" + dto.getUsername() + "' not found.");
        }
        Customer customer = (Customer) customerOptional.get();
        customer.addCredit(dto.getCredit());

        userRepository.save(customer);
    }

    public UserRecord showUserDetails(ShowUserDetails dto) {
        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        if(user.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(user.get() instanceof Customer customer) {
            return new UserRecord(customer.getUsername(), "customer", customer.getEmail(), customer.getAddress(), customer.getBalance());
        } else {
            Admin admin = (Admin) user.get();
            return new UserRecord(admin.getUsername(), "admin", admin.getEmail(), admin.getAddress(), null);
        }
    }

    public PurchaseHistoryRecord showPurchaseHistory(ShowPurchaseHistory dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        List<Purchase> purchases = customer.getPurchasesHistory();
        List<PurchaseRecord> purchaseRecords = purchases.stream()
                .map(Purchase::createRecord)
                .toList();


        return new PurchaseHistoryRecord(customer.getUsername(), purchaseRecords);
    }
    public PurchasedBooksRecord showPurchasedBooks(ShowPurchasedBooks dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        return customer.createPurchasedBooksRecord();
    }
}
