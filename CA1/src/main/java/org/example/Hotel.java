package org.example;

import lombok.Getter;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Comparator;
import java.util.Optional;
import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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

    public Customer findCustomerById(String id) {
        for (Customer customer : this.customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public Room findRoomById(String id) {
        for (Room room : this.rooms) {
            if (room.getRoomId().equals(id)) {
                return room;
            }
        }
        return null;
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

    public Optional<List<String>> getCustomerPhonesByRoomNumber(String roomNumber) {
        List<String> phoneNumbers = bookings.stream()
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


    public void initFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            JsonNode customersNode = rootNode.get("customers");
            if (customersNode != null) {
                for (JsonNode customer : customersNode) {
                    String id = customer.get("id").asText();
                    String name = customer.get("name").asText();
                    String phone = customer.get("phone").asText();
                    int age = customer.get("age").asInt();
                    this.customers.add(
                            new Customer(name, age, phone, id)
                    );
                }
            }

            JsonNode roomsNode = rootNode.get("rooms");
            if (roomsNode != null) {
                for (JsonNode room : roomsNode) {
                    String id = room.get("id").asText();
                    int capacity = room.get("capacity").asInt();
                    this.rooms.add(
                            new Room(id, capacity)
                    );
                }
            }

            JsonNode bookingsNode = rootNode.get("bookings");
            if (bookingsNode != null) {
                for (JsonNode booking : bookingsNode) {
                    String id = booking.get("id").asText();
                    String roomId = booking.get("room_id").asText();
                    String customerId = booking.get("customer_id").asText();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Customer customer = findCustomerById(customerId);
                    Room room = findRoomById(roomId);
                    LocalDateTime checkIn = LocalDateTime.parse(booking.get("check_in").asText(), formatter);
                    LocalDateTime checkOut = LocalDateTime.parse(booking.get("check_out").asText(), formatter);

                    this.bookings.add(
                            new Booking(id, customer, room, checkIn, checkOut)
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
