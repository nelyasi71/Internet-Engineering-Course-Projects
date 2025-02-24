package org.miobook.models;

public class User {
    private String userName;
    private String password;
    private String email;
    private Address address;
    private Wallet wallet;

    public User(String userName, String password, String email, Address address) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.address = address;
        this.wallet = new Wallet();
    }
}