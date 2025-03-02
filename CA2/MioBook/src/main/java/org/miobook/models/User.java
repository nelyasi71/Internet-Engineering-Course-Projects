package org.miobook.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    protected String userName;
    protected String password;
    protected String email;
    protected Address address;

    public User(String userName, String password, String email, Address address) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
    }
}