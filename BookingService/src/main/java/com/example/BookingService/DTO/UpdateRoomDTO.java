package com.example.BookingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoomDTO {
    private Integer roomID;
    private String roomName;
    private Integer capacity;
}
