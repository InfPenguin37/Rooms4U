package com.example.BookingService.Services;

import com.example.BookingService.DTO.LoginDTO;
import com.example.BookingService.DTO.UserDTO;
import com.example.BookingService.Exceptions.ExceptionHandle;
import com.example.BookingService.Models.User;
import com.example.BookingService.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    //SIGN UP
    public ResponseEntity<String> signup(User user) {

        // checking if user already present
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new ExceptionHandle("Forbidden, Account already exists");
        }

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
    }

    //LOGIN
    public ResponseEntity<String> login(LoginDTO login) {
        String email = login.getEmail();
        String password = login.getPassword();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        //checking user's existence
        if (optionalUser.isEmpty()) {
            throw new ExceptionHandle("User does not exist");
        }

        User user = optionalUser.get();

        if (user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.FOUND).body("Login Successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username/Password Incorrect");
        }
    }

    // get user
    public ResponseEntity<?> getUser(Integer userID) {
        Optional<User> optionalUser = userRepository.findByUserID(userID);
        
        if (optionalUser.isEmpty()) {
            throw new ExceptionHandle("User does not exist");
        }

        User user = optionalUser.get();
        UserDTO userDTO = new UserDTO(user.getUserID(), user.getName(), user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    // get all users
    public ResponseEntity<List<UserDTO>> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User user : users) {
            UserDTO userDTO = new UserDTO(user.getUserID(), user.getName(), user.getEmail());
            userDTOS.add(userDTO);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userDTOS);
    }
}
