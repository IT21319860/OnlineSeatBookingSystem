package com.seatreservation.userseatReservation.Controllers;


import com.seatreservation.userseatReservation.Service.UserService;
import com.seatreservation.userseatReservation.dto.JwtResponse;
import com.seatreservation.userseatReservation.dto.UserLoginRequest;
import com.seatreservation.userseatReservation.dto.UserRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.authenticateUser(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
