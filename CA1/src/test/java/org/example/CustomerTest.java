package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void should_ThrowException_When_AgeIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Bob", -1, "987654321", 2);
        });
        assertEquals("Age cannot be negative", exception.getMessage());
    }

    @Test
    void should_ThrowException_When_phoneNumberIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Narges", 20, null, 2);
        });

        assertEquals("PhoneNumber cannot be null", exception.getMessage());
    }

    @Test
    void should_ThrowException_When_NameIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Customer(null, 20, "987654321", 2);
        });

        assertEquals("Name cannot be null", exception.getMessage());
    }

}