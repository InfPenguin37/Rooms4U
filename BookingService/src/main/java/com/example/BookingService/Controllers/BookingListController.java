package com.example.BookingService.Controllers;


import com.example.BookingService.Services.BookingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class BookingListController {
    @Autowired
    private BookingServices bookingServices;

    //get history
    @GetMapping("/history")
    public ResponseEntity<?> getCompletedBookings(@RequestParam Integer userID) {
        return bookingServices.getCompletedBookings(userID);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<?> getUpcomingBookings(@RequestParam Integer userID) {
        return bookingServices.getUpcomingBookings(userID);
    }

    //get all upcoming bookings
    @GetMapping("/allUpcoming")
    public ResponseEntity<?> getAllUpcomingBookings() {
        return bookingServices.getAllUpcomingBookings();
    }
}
