package org.example;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
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

    public List<Room> getRooms(int minCapacity) {
        return rooms.stream()
                .filter(room -> room.getCapacity() > minCapacity)
                .collect(Collectors.toList());
    }

    public Optional<String> getOldestCustomerName() {
        return customers.stream()
                .max(Comparator.comparingInt(Customer::getAge))
                .map(Customer::getName);
    }

    public Optional<List<Integer>> getCustomerPhonesByRoomNumber(String roomNumber) {
        List<Integer> phoneNumbers = bookings.stream()
                .filter(booking -> booking.getBookedRoom().getRoomId().equals(roomNumber))
                .map(booking -> customers.stream()
                        .filter(customer -> customer.getId().equals(booking.getBooker().getId()))
                        .findFirst()
                        .map(Customer::getPhoneNumber)
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return phoneNumbers.isEmpty() ? Optional.empty() : Optional.of(phoneNumbers);
    }

}
