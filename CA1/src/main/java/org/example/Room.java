package org.example;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Room {
    private String roomId;
    private int capacity;

    public Room(String roomId, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
        this.roomId = roomId;
        this.capacity = capacity;
    }
}
