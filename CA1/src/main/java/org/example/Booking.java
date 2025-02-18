package org.example;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@ToString
public class Booking {
    private int id;
    private Customer booker;
    private Room bookedRoom;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;

    public Booking(int id, Customer booker, Room bookedRoom, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException("Check-in date must be before check-out date");
        }
        this.id = id;
        this.booker = booker;
        this.bookedRoom = bookedRoom;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public long getStayDurationInDays() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
