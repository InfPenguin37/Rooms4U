package com.example.BookingService.DTO;

import com.example.BookingService.Models.Room;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDisplayDTO {
    private Integer bookingID;
    private LocalDate dateOfBooking;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeFrom;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeTo;
    
    private UserDisplayDTO user;
    private String roomName;
    private String purpose;

}
