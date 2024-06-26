package com.example.BookingService.Services;

import com.example.BookingService.DTO.*;
import com.example.BookingService.Exceptions.ExceptionHandle;
import com.example.BookingService.Models.Booking;
import com.example.BookingService.Models.Room;
import com.example.BookingService.Repositories.BookingRepository;
import com.example.BookingService.Repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.List;

@Service
public class RoomServices {
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    // create room
    public ResponseEntity<String> createRoom(Room room) {
        String name = room.getRoomName();
        Integer capacity = room.getCapacity();
        
        if(roomRepository.findByRoomName(name).isPresent()){
            throw new ExceptionHandle("Room already exists");
        }
        
        if(capacity<0){
            throw new ExceptionHandle("Invalid capacity");
        }
        
        roomRepository.save(room);
        return ResponseEntity.ok("Room created successfully");
    }

    //get a room
    public ResponseEntity<?> getRoom(Integer capacity) {
        Optional<Room> optionalRoom = roomRepository.findByCapacity(capacity);
        if(optionalRoom.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Error", "Invalid parameters"));
        }

        List<Room> rooms = roomRepository.findAllByCapacity(capacity);

        // Create a list to hold RoomDisplayDTO objects
        List<RoomDisplayDTO> roomDisplayDTOList = new ArrayList<>();

        // Iterate through rooms and construct RoomDisplayDTO objects
        for (Room room : rooms) {
            RoomDisplayDTO roomDisplayDTO = new RoomDisplayDTO();
            roomDisplayDTO.setRoomID(room.getRoomID());
            roomDisplayDTO.setCapacity(room.getCapacity());

            // Get bookings for the current room
            List<Booking> bookings = bookingRepository.findByRoom(room);

            // Create a list to hold BookingDisplayDTO objects for bookings
            List<BookingDisplayDTO> bookingDisplayDTOList = new ArrayList<>();

            // Iterate through bookings and construct BookingDisplayDTO objects
            for (Booking booking : bookings) {
                UserDisplayDTO userDisplayDTO = new UserDisplayDTO(booking.getUser().getUserID());
                BookingDisplayDTO bookingDisplayDTO = new BookingDisplayDTO();
                bookingDisplayDTO.setBookingID(booking.getBookingID());
                bookingDisplayDTO.setDateOfBooking(booking.getDateOfBooking());
                bookingDisplayDTO.setTimeFrom(booking.getTimeFrom());
                bookingDisplayDTO.setTimeTo(booking.getTimeTo());
                bookingDisplayDTO.setPurpose(booking.getPurpose());
                bookingDisplayDTO.setUser(userDisplayDTO);

                // Add BookingDisplayDTO to the list
                bookingDisplayDTOList.add(bookingDisplayDTO);
            }
            // Set bookings list for the current room
            roomDisplayDTO.setBookings(bookingDisplayDTOList);

            // Add RoomDisplayDTO to the list
            roomDisplayDTOList.add(roomDisplayDTO);
        }
        return ResponseEntity.ok(roomDisplayDTOList);
    }

    //edit room
    public ResponseEntity<String> updateRoom(UpdateRoomDTO updateRoomDTO) {
        String roomName = updateRoomDTO.getRoomName();
        Integer capacity = updateRoomDTO.getCapacity();
        
        Optional<Room> optionalRoom = roomRepository.findByRoomID(updateRoomDTO.getRoomID());
        if(capacity<0){
            throw new ExceptionHandle("Invalid capacity");
        }
        
        if (optionalRoom.isEmpty()) {
            throw new ExceptionHandle("Room does not exist");
        }

        Room room = optionalRoom.get();
        room.setRoomName(roomName);
        room.setCapacity(capacity);
        roomRepository.save(room);
        return ResponseEntity.ok("Room edited successfully");
    }

    //delete room
    public ResponseEntity<String> deleteRoom(DeleteRoomDTO deleteRoomDTO) {
        Integer roomID = deleteRoomDTO.getRoomID();
        
        Optional<Room> optionalRoom = roomRepository.findByRoomID(roomID);
        if (optionalRoom.isEmpty()) {
            throw new ExceptionHandle("Room does not exist");
        }

        roomRepository.delete(optionalRoom.get());
        return ResponseEntity.ok("Room deleted successfully");
    }

    //get all rooms
    public ResponseEntity<?> getRooms(){
        List<Room> rooms = roomRepository.findAll();
        List<AvailableRoomDisplayDTO> availableRoomList = new ArrayList<>();
        for (Room room : rooms) {
            AvailableRoomDisplayDTO availableRoomDisplayDTO = new AvailableRoomDisplayDTO();
            availableRoomDisplayDTO.setRoomID(room.getRoomID());
            availableRoomDisplayDTO.setRoomName(room.getRoomName());
            availableRoomDisplayDTO.setCapacity(room.getCapacity());
            availableRoomList.add(availableRoomDisplayDTO);
        }
        return ResponseEntity.ok(availableRoomList);
    }
}
