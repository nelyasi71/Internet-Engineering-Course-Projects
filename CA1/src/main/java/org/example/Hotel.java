package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if(isCustomerExisted(customer.getId())) {
            throw new IllegalArgumentException("The customer is already existed.");
        }
        customers.add(customer);
    }

    public void addRoom(Room room) {
        if(isRoomExisted(room.getId())) {
            throw new IllegalArgumentException("The room is already existed.");
        }
        rooms.add(room);
    }

    public void addBooking(Booking booking) {
        if(!isCustomerExisted(booking.getBooker().getId())) {
            throw new IllegalArgumentException("This customer does not exist.");
        }
        if(!isRoomExisted(booking.getBookedRoom().getId())) {
            throw new IllegalArgumentException("This room does not exist.");
        }
        if (isRoomBooked(booking)) {
            throw new IllegalArgumentException("Room is already booked for the selected dates.");
        }
        bookings.add(booking);
    }

    private boolean isRoomBooked(Booking newBooking) {
        for (Booking existingBooking : bookings) {
            if (existingBooking.getBookedRoom().equals(newBooking.getBookedRoom()) &&
                    (newBooking.getCheckInDate().isBefore(existingBooking.getCheckOutDate()) &&
                            newBooking.getCheckOutDate().isAfter(existingBooking.getCheckInDate()))) {
                return true;
            }
        }
        return false;
    }

    public Customer findCustomerById(int id) {
        return customers.stream().filter(customer -> customer.getId() == id).findFirst().orElse(null);
    }

    public boolean isCustomerExisted(int id) {
        return customers.stream().anyMatch(room -> room.getId() == id);
    }

    public Room findRoomById(int id) {
        return rooms.stream().filter(room -> room.getId() == id).findFirst().orElse(null);
    }

    public boolean isRoomExisted(int id) {
        return rooms.stream().anyMatch(room -> room.getId() == id);
    }


    public List<Room> getRooms(int minCapacity) {
        if (minCapacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        return rooms.stream()
                .filter(room -> room.getCapacity() >= minCapacity)
                .collect(Collectors.toList());
    }

    public Optional<String> getOldestCustomerName() {
        if (customers.isEmpty()) {
            throw new NoSuchElementException("No customers exist");
        }
        return customers.stream()
                .max(Comparator.comparingInt(Customer::getAge))
                .map(Customer::getName);
    }

    public List<String> getCustomerPhonesByRoomNumber(int roomNumber) {
        if (!isRoomExisted(roomNumber)) {
            throw new NoSuchElementException("Room does not exist in the hotel.");
        }

        List<String> phoneNumbers = bookings.stream()
                .filter(booking -> booking.getBookedRoom().getId() == roomNumber)
                .map(booking -> customers.stream()
                        .filter(customer -> customer.getId() == booking.getBooker().getId())
                        .findFirst()
                        .map(Customer::getPhoneNumber)
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return phoneNumbers.isEmpty() ? null : phoneNumbers;
    }

    public void initFromJson(String filePath) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode rootNode = mapper.readTree(new File(filePath));

            JsonNode customersNode = rootNode.get("customers");
            if (customersNode != null) {
                for (JsonNode customer : customersNode) {
                    int id = customer.get("ssn").asInt();
                    String name = customer.get("name").asText();
                    String phone = customer.get("phone").asText();
                    int age = customer.get("age").asInt();
                    addCustomer(new Customer(name, age, phone, id));
                }
            }

            JsonNode roomsNode = rootNode.get("rooms");
            if (roomsNode != null) {
                for (JsonNode room : roomsNode) {
                    int id = room.get("id").asInt();
                    int capacity = room.get("capacity").asInt();
                    addRoom(new Room(id, capacity));
                }
            }

            JsonNode bookingsNode = rootNode.get("bookings");
            if (bookingsNode != null) {
                for (JsonNode booking : bookingsNode) {
                    int id = booking.get("id").asInt();
                    int roomId = booking.get("room_id").asInt();
                    int customerId = booking.get("customer_id").asInt();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    Customer customer = findCustomerById(customerId);
                    Room room = findRoomById(roomId);
                    LocalDateTime checkIn = LocalDateTime.parse(booking.get("check_in").asText(), formatter);
                    LocalDateTime checkOut = LocalDateTime.parse(booking.get("check_out").asText(), formatter);

                    addBooking(new Booking(id, customer, room, checkIn, checkOut));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logState(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode roomsArray = mapper.createArrayNode();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (Room room : rooms) {
            ObjectNode roomNode = mapper.createObjectNode();
            roomNode.put("room_id", room.getId());
            roomNode.put("capacity", room.getCapacity());

            ArrayNode bookingsArray = mapper.createArrayNode();
            for (Booking booking : bookings) {
                if (booking.getBookedRoom().getId() == room.getId()) {
                    ObjectNode bookingNode = mapper.createObjectNode();
                    bookingNode.put("id", booking.getId());

                    ObjectNode customerNode = mapper.createObjectNode();
                    customerNode.put("ssn", booking.getBooker().getId());
                    customerNode.put("name", booking.getBooker().getName());
                    customerNode.put("phone", booking.getBooker().getPhoneNumber());
                    customerNode.put("age", booking.getBooker().getAge());

                    bookingNode.set("customer", customerNode);
                    bookingNode.put("check_in", booking.getCheckInDate().format(formatter));
                    bookingNode.put("check_out", booking.getCheckOutDate().format(formatter));

                    bookingsArray.add(bookingNode);
                }
            }
            roomNode.set("bookings", bookingsArray);
            roomsArray.add(roomNode);
        }

        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), roomsArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
