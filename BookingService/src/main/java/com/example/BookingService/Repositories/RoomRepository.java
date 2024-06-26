package com.example.BookingService.Repositories;

import com.example.BookingService.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.Optional;

@Repository
@Transactional
public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByRoomName(String roomName);

    Optional<Room> findByCapacity(Integer capacity);

    List<Room> findAllByCapacity(Integer capacity);

    Optional<Room> findByRoomID(Integer roomID);
}
