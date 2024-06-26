package com.example.BookingService.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="Booking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue
    @Getter
    private Integer bookingID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "userID")
    @Getter
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="roomID", referencedColumnName = "roomID")
    @Getter
    private Room room;

    private LocalDate dateOfBooking;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeFrom;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeTo;

    private String purpose;

}
