package org.example;

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

    public int getAge() {
        return age;
    }
}
