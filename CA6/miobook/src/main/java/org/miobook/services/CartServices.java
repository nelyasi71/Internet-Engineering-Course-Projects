package org.miobook.services;

import jakarta.transaction.Transactional;
import org.miobook.Exception.MioBookException;
import org.miobook.commands.*;
import org.miobook.models.*;
import org.miobook.repositories.BookRepository;
import org.miobook.repositories.CartRepository;
import org.miobook.repositories.PurchaseRepository;
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

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Transactional
    public void addCart(AddCart dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Optional<Book> book = bookRepository.findByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }

        BuyItem buyItem = new BuyItem(book.get());

        customer.addCart(buyItem);
        buyItem.setCart(customer.getCart());
        userRepository.save(customer);
    }

    @Transactional
    public void removeCart(RemoveCart dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Optional<Book> book = bookRepository.findByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found in the catalog.");
        }

        customer.removeCart(dto.getTitle());
        userRepository.save(customer);
    }

    @Transactional
    public PurchaseCartRecord purchaseCart(PurchaseCart dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Purchase purchase = customer.purchaseCart();
        purchase.setCustomer(customer);

        purchase.getPurchaseItems()
            .forEach(purchaseItem -> {
                purchaseItem.setCart(null);
                purchaseItem.setPurchase(purchase);
            });

        purchaseRepository.save(purchase);

        return new PurchaseCartRecord(
                purchase.size(), purchase.getPrice(), purchase.getDate()
        );
    }

    @Transactional
    public void borrowBook(BorrowBook dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Optional<Book> book = bookRepository.findByTitle(dto.getTitle());
        if(book.isEmpty()) {
            throw new MioBookException("title", "Book with title '" + dto.getTitle() + "' not found.");
        }

        BorrowItem borrowItem = new BorrowItem(book.get(), dto.getDays());
        customer.addCart(borrowItem);
        borrowItem.setCart(customer.getCart());
        userRepository.save(customer);
    }

    public CartRecord showCart(ShowCart dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if(userOpt.isEmpty()) {
            throw new MioBookException("username", "User with username '" + dto.getUsername() + "' not found.");
        }
        if(userOpt.get().getRole().equals("admin")) {
            throw new MioBookException("Not available for 'Admin' role");
        }
        Customer customer = (Customer) userOpt.get();

        Cart cart = customer.getCart();
        List<PurchaseItemRecord> purchaseItemRecords = cart.getItems().stream()
                .map(PurchaseItem::createRecord)
                .toList();

        return new CartRecord(customer.getUsername(), cart.price(), purchaseItemRecords);
    }
}
