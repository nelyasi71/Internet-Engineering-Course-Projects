package org.example;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Room {
    private String roomId;
    private int capacity;

    public Room(String roomId, int capacity) {
        this.roomId = roomId;
        this.capacity = capacity;
    }
}
