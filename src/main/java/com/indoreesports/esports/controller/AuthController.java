package com.indoreesports.esports.controller;

import com.indoreesports.esports.entity.User;
import com.indoreesports.esports.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        session.setAttribute("user", authentication.getPrincipal()); // Store user in session
        return ResponseEntity.ok("Login successful! Session created.");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest.getEmail() == null || registerRequest.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required!");
        }

        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("Email already in use!");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpSession session) {
        session.invalidate(); // Invalidate session on logout
        return ResponseEntity.ok("Logged out successfully!");
    }

    // DTOs for cleaner request handling
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class RegisterRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
