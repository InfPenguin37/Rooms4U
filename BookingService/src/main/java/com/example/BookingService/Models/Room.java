package com.example.BookingService.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="Rooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer roomID;

    private String roomName;
    private Integer capacity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomID", referencedColumnName = "roomID")
    private List<Booking> booking;
}
