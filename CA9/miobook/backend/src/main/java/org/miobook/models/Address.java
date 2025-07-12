package org.miobook.models;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor
public class Address {
    @NotNull
    private String country;
    @NotNull
    private String city;

    public Address(String country, String city) {
        this.country = country;
        this.city = city;
    }
}
