package org.miobook.models;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("admin")
@NoArgsConstructor
public class Admin extends User {

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
}
