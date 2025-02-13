package org.example;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private String bookingID;
    private String bookerName;
    private String bookedRoomId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Booking(String bookingID, String bookerName, String bookedRoomId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.bookingID = bookingID;
        this.bookerName = bookerName;
        this.bookedRoomId = bookedRoomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
