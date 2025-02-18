package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;


class HotelTest {
    private Customer customer;
    private Room room1, room2, room3;
    private Booking booking;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();

        customer = new Customer("Alice", 25, "123456789", 1);
        room1 = new Room(101, 2);
        room2 = new Room(102, 4);
        room3 = new Room(103, 6);

        booking = new Booking(1, customer, room1,
                LocalDateTime.of(2025, 2, 15, 12, 0),
                LocalDateTime.of(2025, 2, 20, 12, 0));

        hotel.addRoom(room1);
        hotel.addRoom(room2);
        hotel.addRoom(room3);

        hotel.addCustomer(customer);
        hotel.addBooking(booking);
    }


    //booking functions check


    @Test
    void Should_ThrowException_When_BookerDoesNotExistInCustomerList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Room room4 = new Room(104, 6);
            Booking booking2 = new Booking(1, customer, room4,
                    LocalDateTime.of(2025, 2, 15, 12, 0),
                    LocalDateTime.of(2025, 2, 20, 12, 0));

            hotel.addBooking(booking2);
        });

        assertEquals("This room does not exist.", exception.getMessage());
    }

    @Test
    void Should_ThrowException_When_RoomDoesNotExistInRoomsList() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Customer customer2 = new Customer("Alice", 25, "123456789", 2);
            Booking booking2 = new Booking(1, customer2, room2,
                    LocalDateTime.of(2025, 2, 15, 12, 0),
                    LocalDateTime.of(2025, 2, 20, 12, 0));

            hotel.addBooking(booking2);
        });

        assertEquals("This customer does not exist.", exception.getMessage());
    }

    @Test
    void Should_ThrowException_When_roomExisted() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Room existedRoom = new Room(101, 2);
            hotel.addRoom(existedRoom);
        });

        assertEquals("The room is already existed.", exception.getMessage());

    }

    @Test
    void Should_ThrowException_When_customerExisted() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Customer existedCustomer = new Customer("Bob", 2 ,"0915151515", 1);
            hotel.addCustomer(existedCustomer);
        });

        assertEquals("The customer is already existed.", exception.getMessage());

    }

    @Test
    void When_CheckInAndCheckOutAre5DaysApart_Expect_Return5Days() {
        assertEquals(5, booking.getStayDurationInDays());
    }

    @Test
    void Should_doBookings_When_roomIsAvailable() {

        Booking booking2 = new Booking(2, customer, room1,
                LocalDateTime.of(2025, 2, 21, 12, 0),
                LocalDateTime.of(2025, 2, 25, 12, 0));
        hotel.addBooking(booking2);

        assertThat(hotel.getBookings()).contains(booking);
        assertThat(hotel.getBookings()).contains(booking2);
    }


    @Test
    void Should_throwException_When_RoomIsAlreadyBooked() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Booking booking1 = new Booking(2, customer, room1,
                    LocalDateTime.of(2025, 2, 18, 12, 0),
                    LocalDateTime.of(2025, 2, 22, 12, 0));
            hotel.addBooking(booking1);
        });

        assertEquals("Room is already booked for the selected dates.", exception.getMessage());
    }

    @Test
    void Should_doBooking_When_multipleBookingInOneDay() {

        Booking booking1 = new Booking(1, customer, room2,
                LocalDateTime.of(2025, 2, 15, 12, 0),
                LocalDateTime.of(2025, 2, 15, 12, 0));
        hotel.addBooking(booking1);

        Booking booking2 = new Booking(2, customer, room2,
                LocalDateTime.of(2025, 2, 15, 13, 0),
                LocalDateTime.of(2025, 2, 15, 14, 0));
        hotel.addBooking(booking2);
    }

    //hotel functions check
    @Test
    void should_Return1Room_When_MinCapacityIsMetBy1Room() {
        List<Room> largeRooms = hotel.getRooms(5);
        assertEquals(1, largeRooms.size());
    }

    @Test
    void should_Return2Rooms_When_MinCapacityIsMetBy2Rooms() {
        List<Room> largeRooms = hotel.getRooms(3);
        assertEquals(2, largeRooms.size());
    }

    @Test
    void should_ReturnEmptyList_When_NoRoomMeetsMinCapacity() {
        List<Room> largeRooms = hotel.getRooms(8);
        assertEquals(0, largeRooms.size());
    }

    @Test
    void should_ReturnAllRooms_When_MinCapacityIsZero() {
        List<Room> largeRooms = hotel.getRooms(0);
        assertEquals(3, largeRooms.size());
    }

    @Test
    void should_ReturnEmptyList_When_NoRoomExist() {
        Hotel hotel = new Hotel();
        List<Room> largeRooms = hotel.getRooms(10);
        assertTrue(largeRooms.isEmpty());
    }

    @Test
    void should_ThrowIllegalArgumentException_When_minCapacityIsNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            hotel.getRooms(-1);
        });

        assertEquals("Capacity cannot be negative", exception.getMessage());
    }

    @Test
    void should_ReturnJane_When_OldestCustomerIsJane() {
        hotel.addCustomer(new Customer("John", 30, "123456789", 11));
        hotel.addCustomer(new Customer("Jane", 40, "987654321", 12));
        hotel.addCustomer(new Customer("Ben", 22, "987655321", 13));

        Optional<String> oldestCustomer = hotel.getOldestCustomerName();

        assertTrue(oldestCustomer.isPresent());
        assertEquals("Jane", oldestCustomer.get());
    }

    @Test
    void should_ReturnOneFirstOldestCustomer_When_MultipleHaveSameAge() {
        hotel.addCustomer(new Customer("John", 40, "123456789", 11));
        hotel.addCustomer(new Customer("Jane", 40, "987654321", 12));
        hotel.addCustomer(new Customer("Ben", 22, "987655321", 13));

        Optional<String> oldestCustomer = hotel.getOldestCustomerName();

        assertTrue(oldestCustomer.isPresent());
        assertEquals("John", oldestCustomer.get());
    }

    @Test
    void should_ThrowNoSuchElementException_When_NoCustomersExist() {
        Hotel hotel = new Hotel();
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, hotel::getOldestCustomerName);
        assertEquals("No customers exist", exception.getMessage());
    }

    @Test
    void should_ReturnPhoneNumber_When_RoomHasOneBooking() {
        hotel.addBooking(new Booking(1, customer, room2, LocalDateTime.now(), LocalDateTime.now().plusDays(2)));
        List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber(102);
        assertEquals(1, phoneNumbers.size());
        assertEquals("123456789", phoneNumbers.getFirst());
    }

    @Test
    void should_ReturnMultiplePhoneNumbers_When_RoomHasMultipleBookings() {
        Customer customer1 = new Customer("Hassan", 40, "9876543210", 2);
        Customer customer2 = new Customer("Hasti", 40, "9395004426", 3);

        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);

        hotel.addBooking(new Booking(2, customer1, room3, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(6)));
        hotel.addBooking(new Booking(3, customer2, room3, LocalDateTime.now().plusDays(7), LocalDateTime.now().plusDays(9)));

        List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber(103);

        assertEquals(2, phoneNumbers.size());
        assertTrue(phoneNumbers.contains("9876543210"));
        assertTrue(phoneNumbers.contains("9395004426"));
    }

    @Test
    void should_ReturnEmpty_When_RoomHasNoBookings() {
        Room room = new Room(105, 2);
        hotel.addRoom(room);
        List<String> phoneNumbers = hotel.getCustomerPhonesByRoomNumber(105);
        assertNull(phoneNumbers);
    }

    @Test
    void should_ThrowException_When_RoomDoesNotExist() {
        assertThrows(NoSuchElementException.class, () -> hotel.getCustomerPhonesByRoomNumber(999));
    }
}


