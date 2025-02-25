package com.seatreservation.userseatReservation.Service;


import com.seatreservation.userseatReservation.Entity.User;
import com.seatreservation.userseatReservation.Repositories.UserRepository;
import com.seatreservation.userseatReservation.Utility.JwtUtil;
import com.seatreservation.userseatReservation.dto.UserLoginRequest;
import com.seatreservation.userseatReservation.dto.UserRegistrationRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public void registerUser(UserRegistrationRequest request) {
        User user = new User();
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setEmail(request.getEmail()); // Ensure email is set
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public String authenticateUser(UserLoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // Assuming JwtUtil is injected or accessed in another way
                return jwtUtil.generateToken(user);
            }
        }
        throw new RuntimeException("Invalid credentials");
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
