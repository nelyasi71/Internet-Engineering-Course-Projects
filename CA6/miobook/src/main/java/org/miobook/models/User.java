package org.miobook.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.miobook.responses.PurchasedBooksRecord;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
@Getter @Setter
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    protected String username;
    protected String password;
    protected String email;

    @Embedded
    protected Address address;

    public abstract String getRole();
}