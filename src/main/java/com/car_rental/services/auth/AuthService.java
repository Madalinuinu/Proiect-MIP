package com.car_rental.services.auth;


import com.car_rental.dto.SignupRequest;
import com.car_rental.dto.UserDto;

public interface AuthService {

    UserDto createCustomer(SignupRequest signupRequest);

    boolean hasCustomerWithEmail(String email);
}
