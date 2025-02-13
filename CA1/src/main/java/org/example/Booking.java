package org.example;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {
    private String bookingID;
    private Customer booker;
    private String bookedRoomId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Booking(String bookingID, Customer booker, String bookedRoomId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.bookedRoomId = bookedRoomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
