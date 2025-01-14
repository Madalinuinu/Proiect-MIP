package com.car_rental.services.auth;

import com.car_rental.dto.UserDto;
import com.car_rental.dto.SignupRequest;

import java.util.List;

public interface AuthService {

    boolean hasCustomerWithEmail(String email);

    boolean hasCustomerWithName(String name);

    UserDto createCustomer(SignupRequest signupRequest);

    List<UserDto> getAllUsers();

    List<UserDto> sortUsersByName();
}
