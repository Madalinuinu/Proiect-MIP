package com.car_rental.controller;

import com.car_rental.dto.SignupRequest;
import com.car_rental.dto.UserDto;
import com.car_rental.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignupRequest signupRequest) {
        Map<String, String> response = new HashMap<>();


        if (authService.hasCustomerWithEmail(signupRequest.getEmail())) {
            response.put("error", "Email already in use. Please choose a different one.");
            return ResponseEntity.badRequest().body(response);
        }

        if (authService.hasCustomerWithName(signupRequest.getName())) {
            response.put("error", "Name already in use. Please choose a different one.");
            return ResponseEntity.badRequest().body(response);
        }


        authService.createCustomer(signupRequest);
        response.put("success", "Signup successful!");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDto> users = authService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/users/sorted")
    public String getSortedUsers(Model model) {
        List<UserDto> sortedUsers = authService.sortUsersByName();
        model.addAttribute("users", sortedUsers);
        return "sorted";
    }
}
