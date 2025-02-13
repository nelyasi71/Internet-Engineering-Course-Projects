package org.example;

public class Room {
    private String roomId;
    private int capacity;

    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
    }

    public String getRoomId() {
        return roomId;
    }

    public int getCapacity() {
        return capacity;
    }
}
