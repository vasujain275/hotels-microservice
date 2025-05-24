package me.vasujain.hotel.UserService.repository;

import me.vasujain.hotel.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String email);

}
