package com.example.BookingService.Services;

import com.example.BookingService.DTO.*;
import com.example.BookingService.Exceptions.*;
import com.example.BookingService.Models.Booking;
import com.example.BookingService.Models.Room;
import com.example.BookingService.Models.User;
import com.example.BookingService.Repositories.BookingRepository;
import com.example.BookingService.Repositories.RoomRepository;
import com.example.BookingService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServices {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;

    // create booking
    public ResponseEntity<String> createBooking(BookingRequestDTO bookingRequestDTO){
        Integer userID = bookingRequestDTO.getUserID();
        Integer roomID = bookingRequestDTO.getRoomID();

        Optional<User> optionalUser = userRepository.findByUserID(userID);
        Optional<Room> optionalRoom = roomRepository.findByRoomID(roomID);

        // check if user exists
        if(optionalUser.isEmpty()){
            throw new ExceptionHandle("User does not exist");
        }

        User user = optionalUser.get();

        // check if room exists
        if(optionalRoom.isEmpty()){
            throw new ExceptionHandle("Room does not exist");
        }

        Room room = optionalRoom.get();

        LocalDate date = bookingRequestDTO.getDateOfBooking();
        LocalTime timeFrom = bookingRequestDTO.getTimeFrom();
        LocalTime timeTo = bookingRequestDTO.getTimeTo();

        // check if booking is valid
        if(timeTo.isBefore(timeFrom)){
            throw new ExceptionHandle("Invalid date/time");
        }

        List<Booking> bookings = room.getBooking();

        for(Booking booking : bookings){
            if(date.isEqual(booking.getDateOfBooking())){
                if((timeFrom.isBefore(booking.getTimeFrom()) || timeFrom == booking.getTimeFrom()) && (timeTo.isAfter(booking.getTimeFrom()) || timeTo == booking.getTimeTo())){
                    throw new ExceptionHandle("Room unavailable");
                }
                if((timeFrom.isAfter(booking.getTimeFrom()) || timeFrom == booking.getTimeFrom() ) && (timeFrom.isBefore(booking.getTimeTo()) || timeTo == booking.getTimeTo())){
                    throw new ExceptionHandle("Room unavailable");
                }
            }
        }

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        if(date.isBefore(currentDate)) {
            throw new ExceptionHandle("Invalid date/time");
        }
        if(date.isEqual(currentDate)) {
            if (timeFrom.isBefore(currentTime) || timeTo.isBefore(currentTime)) {
                throw new ExceptionHandle("Invalid date/time");
            }
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRoom(room);
        booking.setDateOfBooking(date);
        booking.setTimeFrom(bookingRequestDTO.getTimeFrom());
        booking.setTimeTo(bookingRequestDTO.getTimeTo());
        booking.setPurpose(bookingRequestDTO.getPurpose());
        bookingRepository.save(booking);
        return ResponseEntity.ok("Booking created successfully");
    }

    // edit booking
    public ResponseEntity<String> updateBooking(BookingUpdateDTO bookingUpdateDTO){
        Integer userID = bookingUpdateDTO.getUserID();

        Optional<User> optionalUser = userRepository.findByUserID(userID);
        if(optionalUser.isEmpty()){
            throw new ExceptionHandle("User does not exist");
        }

        Integer roomID = bookingUpdateDTO.getRoomID();

        Optional<Room> optionalRoom = roomRepository.findByRoomID(roomID);
        if(optionalRoom.isEmpty()){
            throw new ExceptionHandle("Room does not exist");
        }
        Room room = optionalRoom.get();


        Integer bookingID = bookingUpdateDTO.getBookingID();

        LocalDate date = bookingUpdateDTO.getDateOfBooking();
        LocalTime timeFrom = bookingUpdateDTO.getTimeFrom();
        LocalTime timeTo = bookingUpdateDTO.getTimeTo();

        Optional<Booking> optionalBooking = bookingRepository.findByBookingID(bookingID);
        if(optionalBooking.isEmpty()){
            throw new ExceptionHandle("Room does not exist");
        }

        Booking bookingUpdate = optionalBooking.get();

        List<Booking> bookings = room.getBooking();

        for(Booking booking : bookings){
            if(!booking.getBookingID().equals(bookingID)) {
                if (date.isEqual(booking.getDateOfBooking())) {
                    if ((timeFrom.isBefore(booking.getTimeFrom()) || (timeFrom == booking.getTimeFrom()) && ((timeTo.isAfter(booking.getTimeFrom()) || timeTo == booking.getTimeTo())))) {
                        throw new ExceptionHandle("Room unavailable");
                    }
                    if ((timeFrom.isAfter(booking.getTimeFrom()) || timeFrom == booking.getTimeFrom()) && (timeFrom.isBefore(booking.getTimeTo()) || timeTo == booking.getTimeTo())) {
                        throw new ExceptionHandle("Room unavailable");
                    }
                }
            }
        }

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        if(date.isBefore(currentDate)) {
            throw new ExceptionHandle("Invalid date/time");
        }
        if(date.isEqual(currentDate)) {
            if (timeFrom.isBefore(currentTime) || timeTo.isBefore(currentTime)) {
                throw new ExceptionHandle("Invalid date/time");
            }
        }

        bookingUpdate.setDateOfBooking(date);
        bookingUpdate.setTimeFrom(timeFrom);
        bookingUpdate.setTimeTo(timeTo);
        bookingUpdate.setPurpose(bookingUpdateDTO.getPurpose());
        bookingRepository.save(bookingUpdate);
        return ResponseEntity.ok("Booking modified successfully");
    }

    // delete booking
    public ResponseEntity<String> deleteBooking(Integer bookingID){
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingID);
        if(optionalBooking.isEmpty()){
            throw new ExceptionHandle("Booking does not exist");
        }

        Booking booking = optionalBooking.get();
        bookingRepository.delete(booking);
        return ResponseEntity.ok("Booking deleted successfully");
    }

    // get booking history
    public ResponseEntity<?> getCompletedBookings(Integer userID){
        Optional<User> optionalUser = userRepository.findByUserID(userID);
        if(optionalUser.isEmpty()){
            throw new ExceptionHandle("User does not exist");
        }

        List<Booking> bookingHistory = bookingRepository.findHistoryByUserId(userID);
        List<BookingDisplayDTO> bookingHistoryDisplay = new ArrayList<>();
        for(Booking booking : bookingHistory){
            UserDisplayDTO userDisplayDTO = new UserDisplayDTO(userID);
            BookingDisplayDTO bookingDisplayDTO = new BookingDisplayDTO();
            bookingDisplayDTO.setBookingID(booking.getBookingID());
            bookingDisplayDTO.setDateOfBooking(booking.getDateOfBooking());
            bookingDisplayDTO.setTimeFrom(booking.getTimeFrom());
            bookingDisplayDTO.setTimeTo(booking.getTimeTo());
            bookingDisplayDTO.setPurpose(booking.getPurpose());
            bookingDisplayDTO.setRoomName(booking.getRoom().getRoomName());
            bookingDisplayDTO.setUser(userDisplayDTO);
            bookingHistoryDisplay.add(bookingDisplayDTO);
        }
        return ResponseEntity.ok(bookingHistoryDisplay);
    }

    // get a user's upcoming bookings
    public ResponseEntity<?> getUpcomingBookings(Integer userID){
        Optional<User> optionalUser = userRepository.findByUserID(userID);

        if(optionalUser.isEmpty()){
            throw new ExceptionHandle("User does not exist");
        }

        List<Booking> upcomingBookings = bookingRepository.findUpcomingBookingsByUserID(userID);
        List<BookingDisplayDTO> upcomingBookingDisplay = new ArrayList<>();

        for(Booking booking : upcomingBookings){
            UserDisplayDTO user = new UserDisplayDTO(userID);
            BookingDisplayDTO upcomingBookingDisplayDTO = new BookingDisplayDTO();
            upcomingBookingDisplayDTO.setBookingID(booking.getBookingID());
            upcomingBookingDisplayDTO.setUser(user);
            upcomingBookingDisplayDTO.setDateOfBooking(booking.getDateOfBooking());
            upcomingBookingDisplayDTO.setTimeFrom(booking.getTimeFrom());
            upcomingBookingDisplayDTO.setTimeTo(booking.getTimeTo());
            upcomingBookingDisplayDTO.setRoomName(booking.getRoom().getRoomName());
            upcomingBookingDisplayDTO.setPurpose(booking.getPurpose());
            upcomingBookingDisplay.add(upcomingBookingDisplayDTO);
        }
        return ResponseEntity.ok(upcomingBookingDisplay);
    }

    //get all upcoming bookings
    public ResponseEntity<?> getAllUpcomingBookings(){
        List<Booking> upcomingBookings = bookingRepository.findAll();
        List<UpcomingAllDIsplayDTO> upcomingBookingDisplay = new ArrayList<>();

        for(Booking booking : upcomingBookings){
            UserNameDisplayDTO user = new UserNameDisplayDTO(booking.getUser().getName());
            UpcomingAllDIsplayDTO upcomingBookingDisplayDTO = new UpcomingAllDIsplayDTO();
            upcomingBookingDisplayDTO.setBookingID(booking.getBookingID());
            upcomingBookingDisplayDTO.setUser(user);
            upcomingBookingDisplayDTO.setDateOfBooking(booking.getDateOfBooking());
            upcomingBookingDisplayDTO.setTimeFrom(booking.getTimeFrom());
            upcomingBookingDisplayDTO.setTimeTo(booking.getTimeTo());
            upcomingBookingDisplayDTO.setPurpose(booking.getPurpose());
            upcomingBookingDisplayDTO.setRoomName(booking.getRoom().getRoomName());
            upcomingBookingDisplay.add(upcomingBookingDisplayDTO);
        }
        return ResponseEntity.ok(upcomingBookingDisplay);
    }
}
