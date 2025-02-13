package org.example;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private List<Customer> customers;
    private List<Room> rooms;
    private List<Booking> bookings;

    public Hotel() {
        this.customers = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}
