package com.car_rental.controller;

import com.car_rental.dto.SignupRequest;
import com.car_rental.dto.UserDto;
import com.car_rental.services.auth.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Main Page Mapping ("/")
    @GetMapping("/")
    public String mainPage() {
        return "index";  // Renders index.html
    }

    // Sign Up Page Mapping ("/signup")
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";  // Renders signup.html
    }

    // Signup Handling
    @PostMapping("/signup")
    public ResponseEntity<?> signupCustomer(@RequestBody SignupRequest signupRequest) {
        if (authService.hasCustomerWithEmail(signupRequest.getEmail()))
            return new ResponseEntity<>("Customer already exists with this email", HttpStatus.NOT_ACCEPTABLE);
        UserDto createdCustomerDto = authService.createCustomer(signupRequest);
        if (createdCustomerDto == null)
            return new ResponseEntity<>("Customer not created, try again later", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(createdCustomerDto, HttpStatus.CREATED);
    }
}
