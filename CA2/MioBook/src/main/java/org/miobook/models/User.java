package org.miobook.models;

import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.PurchasedBooksRecord;

@Getter
@Setter
public class User {
    protected String username;
    protected String password;
    protected String email;
    protected Address address;

    public User(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

}