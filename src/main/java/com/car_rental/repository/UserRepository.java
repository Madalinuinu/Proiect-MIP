package com.car_rental.repository;

import com.car_rental.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findByName(String name);
    Optional<User> findFirstByEmail(String email);

}
