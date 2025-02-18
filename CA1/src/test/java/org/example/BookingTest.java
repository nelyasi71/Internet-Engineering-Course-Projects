package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BookingTest {

    private Customer customer;
    private Room room;

    @BeforeEach
    void setUp() {
        customer = new Customer("Alice", 25, "123456789", 1);
        room = new Room(101, 2);
    }

    @Test
    void Should_ThrowException_When_CheckOutIsBeforeCheckIn() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Booking(1, customer, room,
                    LocalDateTime.of(2025, 2, 15, 12, 0),
                    LocalDateTime.of(2025, 2, 10, 12, 0));
        });

        assertEquals("Check-in date must be before check-out date", exception.getMessage());
    }

}