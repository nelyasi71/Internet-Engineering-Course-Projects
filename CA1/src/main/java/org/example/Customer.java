package org.example;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {
    private String name;
    private int age;
    private String phoneNumber ;
    private int id;

    public Customer(String name, int age, String phoneNumber, int id) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        if (phoneNumber == null) {
            throw new IllegalArgumentException("PhoneNumber cannot be null");
        }
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }
}
