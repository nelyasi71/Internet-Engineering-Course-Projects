package org.miobook.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    @NotNull
    private String country;
    @NotNull
    private String city;

    public Address() {}
    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
