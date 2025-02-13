package org.example;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@ToString
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

    public long getStayDurationInDays() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
