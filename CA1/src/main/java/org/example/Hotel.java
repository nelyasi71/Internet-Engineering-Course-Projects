package org.example;

import java.util.List;

public class Hotel {
    private List<Customer> customers;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel(List<Customer> customers, List<Room> rooms, List<Booking> bookings) {
        this.customers = customers;
        this.rooms = rooms;
        this.bookings = bookings;
    }
}
