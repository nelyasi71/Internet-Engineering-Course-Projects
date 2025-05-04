package org.miobook.models;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("admin")
@NoArgsConstructor
public class Admin extends User {
    @OneToMany(mappedBy = "addedBy", cascade = CascadeType.ALL)
    private List<Book> books = new ArrayList<>();

    public Admin(String userName, String password, String email, Address address) {
        this.username = userName;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    @Override
    public String getRole() {
        return "admin";
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}
