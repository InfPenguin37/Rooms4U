package com.example.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDisplayDTO {
    private Integer roomID;
    private Integer capacity;
    List<BookingDisplayDTO> bookings;
}
