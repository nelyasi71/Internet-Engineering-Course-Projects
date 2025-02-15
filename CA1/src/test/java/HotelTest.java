package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class BookingTest {

    private Customer customer1, customer2;
    private Room room1, room2;
    private Booking booking1, booking2;

    @BeforeEach
    void setUp() {
        // Creating customers
        customer1 = new Customer("Alice", 25, "123456789", "1");
        customer2 = new Customer("Bob", 40, "987654321", "2");

        // Creating rooms
        room1 = new Room("101", 2);
        room2 = new Room("102", 5);

        // Creating bookings
        booking1 = new Booking("B001", customer1, room1,
                LocalDateTime.of(2025, 2, 15, 12, 0),
                LocalDateTime.of(2025, 2, 20, 12, 0));

        booking2 = new Booking("B002", customer2, room2,
                LocalDateTime.of(2025, 3, 10, 15, 0),
                LocalDateTime.of(2025, 3, 15, 11, 0));

        Hotel hotel = new Hotel();

        hotel.addRoom(room1);
        hotel.addRoom(room2);

        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);

        hotel.addBooking(booking1);
        hotel.addBooking(booking2);
    }

//    @Test
//    void Should_CalculateCorrectStayDuration_When_ValidDatesProvided() {
//        assertEquals(4, booking1.getStayDurationInDays());
//        assertEquals(5, booking2.getStayDurationInDays());
//    }
//
//    @Test
//    void Should_ReturnCorrectBookingID_When_BookingIsCreated() {
//        assertEquals("B001", booking1.getBookingID());
//        assertEquals("B002", booking2.getBookingID());
//    }
//
//    @Test
//    void Should_AssociateCorrectCustomer_When_BookingIsMade() {
//        assertEquals(customer1, booking1.getBooker());
//        assertEquals(customer2, booking2.getBooker());
//    }
//
//    @Test
//    void Should_AssociateCorrectRoom_When_BookingIsMade() {
//        assertEquals(room1, booking1.getBookedRoom());
//        assertEquals(room2, booking2.getBookedRoom());
//    }
//
//    @Test
//    void Should_ReturnCorrectCheckInDate_When_BookingIsMade() {
//        assertEquals(LocalDateTime.of(2025, 2, 1, 14, 0), booking1.getCheckInDate());
//        assertEquals(LocalDateTime.of(2025, 3, 10, 15, 0), booking2.getCheckInDate());
//    }
//
//    @Test
//    void Should_ReturnCorrectCheckOutDate_When_BookingIsMade() {
//        assertEquals(LocalDateTime.of(2025, 2, 5, 12, 0), booking1.getCheckOutDate());
//        assertEquals(LocalDateTime.of(2025, 3, 15, 11, 0), booking2.getCheckOutDate());
//    }
    @test
    void When_CheckInAndCheckOutAre5DaysApart_Expect_Return5Days(){
        assertEquals(5, booking2.getStayDurationInDays());
    }

}
