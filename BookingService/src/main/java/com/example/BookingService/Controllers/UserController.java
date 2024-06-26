package com.example.BookingService.Controllers;

import com.example.BookingService.DTO.LoginDTO;
import com.example.BookingService.DTO.UserDTO;
import com.example.BookingService.Models.User;
import com.example.BookingService.Repositories.UserRepository;
import com.example.BookingService.Services.UserServices;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin
public class UserController {
    @Autowired
    private UserServices userServices;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        return userServices.signup(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO login) {
        return userServices.login(login);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestParam Integer userID) {
        return userServices.getUser(userID);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return userServices.getAllUser();
    }

}
