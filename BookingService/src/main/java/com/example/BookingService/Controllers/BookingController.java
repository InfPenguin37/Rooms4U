package com.example.BookingService.Controllers;

import com.example.BookingService.DTO.BookingRequestDTO;
import com.example.BookingService.DTO.BookingUpdateDTO;
import com.example.BookingService.Services.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookingController {
    @Autowired
    private BookingServices bookingServices;

    //create booking
    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        return bookingServices.createBooking(bookingRequestDTO);
    }

    //edit booking
    @PatchMapping
    public ResponseEntity<String> updateBooking(@RequestBody BookingUpdateDTO bookingUpdateDTO) {
        return bookingServices.updateBooking(bookingUpdateDTO);
    }

    //delete booking
    @DeleteMapping
    public ResponseEntity<String> deleteBooking(@RequestParam Integer bookingID) {
        return bookingServices.deleteBooking(bookingID);
    }
}
