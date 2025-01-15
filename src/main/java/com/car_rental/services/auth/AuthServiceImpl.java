package com.car_rental.services.auth;

import com.car_rental.dto.UserDto;
import com.car_rental.dto.SignupRequest;
import com.car_rental.entity.User;
import com.car_rental.repository.UserRepository;
import com.car_rental.enums.UserRole;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean hasCustomerWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @Override
    public boolean hasCustomerWithName(String name) {
        return userRepository.findAll().stream()
                .anyMatch(user -> user.getName() != null && user.getName().equalsIgnoreCase(name));
    }

    @Override
    public UserDto createCustomer(SignupRequest signupRequest) {
        User newUser = new User();
        newUser.setName(signupRequest.getName());
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPassword(signupRequest.getPassword());
        newUser.setUserRole(UserRole.CUSTOMER);

        userRepository.save(newUser);

        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setName(newUser.getName());
        userDto.setEmail(newUser.getEmail());
        userDto.setUserRole(newUser.getUserRole());


        try {
            saveUserToFile(userDto);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setUserRole(user.getUserRole());
            userDtos.add(userDto);
        }

        return userDtos;
    }

    @Override
    public List<UserDto> sortUsersByName() {
        List<UserDto> users = getAllUsers();
        users.sort((user1, user2) -> {
            String name1 = user1.getName();
            String name2 = user2.getName();

            if (name1 == null && name2 == null) {
                return 0;
            }
            if (name1 == null) {
                return 1;
            }
            if (name2 == null) {
                return -1;
            }

            return name1.compareTo(name2);
        });
        return users;
    }


    private void saveUserToFile(UserDto userDto) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
        writer.write("ID: " + userDto.getId() + ", Name: " + userDto.getName() + ", Email: " + userDto.getEmail() + ", Role: " + userDto.getUserRole());
        writer.newLine();
        writer.close();
    }


    public List<UserDto> loadUsersFromFile() throws IOException {
        List<UserDto> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(", ");
            UserDto userDto = new UserDto();
            userDto.setId(Long.parseLong(parts[0].split(": ")[1]));
            userDto.setName(parts[1].split(": ")[1]);
            userDto.setEmail(parts[2].split(": ")[1]);
            userDto.setUserRole(UserRole.valueOf(parts[3].split(": ")[1]));
            users.add(userDto);
        }
        reader.close();
        return users;
    }
}
