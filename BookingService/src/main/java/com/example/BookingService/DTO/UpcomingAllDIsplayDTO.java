package com.example.BookingService.DTO;

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
public class UpcomingAllDIsplayDTO {
    private Integer bookingID;
    private LocalDate dateOfBooking;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeFrom;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime timeTo;

    private UserNameDisplayDTO user;
    private String roomName;
    private String purpose;
}
