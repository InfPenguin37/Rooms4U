package com.example.BookingService.Repositories;

import com.example.BookingService.DTO.BookingDisplayDTO;
import com.example.BookingService.Models.Booking;
import com.example.BookingService.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByRoomAndDateOfBooking(Room room, LocalDate date);

    List<Booking> findByRoom(Room room);

    Optional<Booking> findByBookingID(Integer bookingID);

    @Query("SELECT b FROM Booking b WHERE b.user.userID = :userID AND b.dateOfBooking >= CURRENT_DATE AND  b.timeFrom >= CURRENT_TIME")
    List<Booking> findUpcomingBookingsByUserID(Integer userID);

    @Query("SELECT b FROM Booking b WHERE b.user.userID = :userID AND b.dateOfBooking <= CURRENT_DATE AND  b.timeFrom <= CURRENT_TIME")
    List<Booking> findHistoryByUserId(Integer userID);
}
