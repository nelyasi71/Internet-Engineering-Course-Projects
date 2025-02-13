package org.example;
import java.time.LocalDateTime;

public class Booking {
    private String bookingID;
    private Customer booker;
    private Room bookedRoom;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Booking(String bookingID, Customer booker, Room bookedRoom, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        this.bookingID = bookingID;
        this.booker = booker;
        this.bookedRoom = bookedRoom;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }
}
