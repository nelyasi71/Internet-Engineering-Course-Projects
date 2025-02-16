package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


class HotelTest {
    private Customer customer1, customer2;
    private Room room1, room2,room3;
    private Booking booking1, booking2;
    private Hotel hotel;
    private Hotel roomLessHotel;
    @BeforeEach
    void setUp() {
        customer1 = new Customer("Alice", 25, "123456789", "1");
        customer2 = new Customer("Bob", 40, "987654321", "2");

        room1 = new Room("101", 2);
        room2 = new Room("102", 4);
        room3 = new Room("103", 6);

        booking1 = new Booking("B001", customer1, room1,
                LocalDateTime.of(2025, 2, 15, 12, 0),
                LocalDateTime.of(2025, 2, 20, 12, 0));

        booking2 = new Booking("B002", customer2, room2,
                LocalDateTime.of(2025, 3, 15, 15, 0),
                LocalDateTime.of(2025, 3, 10, 11, 0));

        hotel.addRoom(room1);
        hotel.addRoom(room2);

        hotel.addCustomer(customer1);
        hotel.addCustomer(customer2);

        hotel.addBooking(booking1);
        hotel.addBooking(booking2);


    }
    
    @Test
    void When_CheckInAndCheckOutAre5DaysApart_Expect_Return5Days(){
        assertEquals(5, booking1.getStayDurationInDays());
    }

    @Test
    void Should_ThrowException_When_CheckOutDateIsBeforeCheckInDate(){}

    @Test
    void should_Return1Rooms_When_MinCapacityIsMetBy1Room(){
        List<Room> largeRooms = hotel.getRooms(5);
        assertEquals(1,largeRooms.size());
    }

    @Test
    void should_Return2Rooms_When_MinCapacityIsMetBy2Room(){
        List<Room> largeRooms = hotel.getRooms(1);
        assertEquals(2,largeRooms.size());
    }

    @Test
    void should_ReturnEmptyList_When_NoRoomMeetsMinCapacity() {
        List<Room> largeRooms = hotel.getRooms(8);
        assertNull(largeRooms);
    }

    @Test
    void should_ReturnAllRooms_When_MinCapacityIsZero() {
        List<Room> largeRooms = hotel.getRooms(0);
        assertEquals(3, largeRooms.size());
    }

    @Test
    void should_ReturnEmptyList_When_NoRoomsExist(){
        List<Room> largeRooms = roomLessHotel.getRooms(0);
        assertNull(largeRooms);
    }

}