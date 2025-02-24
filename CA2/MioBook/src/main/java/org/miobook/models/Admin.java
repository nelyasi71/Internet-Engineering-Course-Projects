package org.miobook.models;

public class Admin extends User {
    public Admin(String userName, String password, String email, Address address) {
        super(userName, password, email, address);
    }
}
