package org.example;
import lombok.Getter;

@Getter
public class Customer {
    private String name;
    private int age;
    private int phoneNumber ;
    private String id;

    public Customer(String name, int age, int phoneNumber, String id) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.id = id;

    }
}
