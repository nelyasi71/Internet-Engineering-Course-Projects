package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void Should_ThrowException_When_RoomCapacityIsNotPositive() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Room("105", 0);
        });

        assertEquals("Room capacity must be greater than zero", exception.getMessage());
    }

}