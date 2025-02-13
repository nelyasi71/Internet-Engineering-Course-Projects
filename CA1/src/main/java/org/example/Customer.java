package org.example;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Customer {
    private String name;
    private int age;
    private String phoneNumber ;
    private String id;

    public Customer(String name, int age, String phoneNumber, String id) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public int getAge() {
        return age;
    }
}
