package org.example;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Room {
    private String id;
    private int capacity;

    public Room(String id, int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Room capacity must be greater than zero");
        }
        this.id = id;
        this.capacity = capacity;
    }
}
