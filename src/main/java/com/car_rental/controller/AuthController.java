package com.car_rental.controller;

import com.car_rental.dto.SignupRequest;
import com.car_rental.dto.UserDto;
import com.car_rental.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignupRequest signupRequest) {
        try {

            authService.createCustomer(signupRequest);
            return "redirect:/";
        } catch (Exception e) {
            return "error";
        }
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
