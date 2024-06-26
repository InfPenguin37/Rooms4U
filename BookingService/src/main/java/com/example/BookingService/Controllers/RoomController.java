package com.example.BookingService.Controllers;

import com.example.BookingService.DTO.DeleteRoomDTO;
import com.example.BookingService.DTO.UpdateRoomDTO;
import com.example.BookingService.Models.Room;
import com.example.BookingService.Services.RoomServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomServices roomServices;

    //create a room
    @PostMapping
    public ResponseEntity<String> createRoom(@RequestBody Room room) {
        return roomServices.createRoom(room);
    }

    //get room
    @GetMapping
    public ResponseEntity<?> getRoomsWithCapacity(Integer capacity) {
        return roomServices.getRoom(capacity);
    }

    //edit a room
    @PatchMapping
    public ResponseEntity<String> updateRoom(@RequestBody UpdateRoomDTO updateRoomDTO) {
        return roomServices.updateRoom(updateRoomDTO);
    }

    //delete a room
    @DeleteMapping
    public ResponseEntity<String> deleteRoom(@RequestBody DeleteRoomDTO deleteRoomDTO) {
        return roomServices.deleteRoom(deleteRoomDTO);
    }

    //get all rooms
    @GetMapping
    @RequestMapping("/all")
    public ResponseEntity<?> getAllRooms(){
        return roomServices.getRooms();
    }
}
