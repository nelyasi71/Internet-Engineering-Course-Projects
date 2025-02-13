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
}
