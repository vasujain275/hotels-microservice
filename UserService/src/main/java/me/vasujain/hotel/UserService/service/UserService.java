package me.vasujain.hotel.UserService.service;

import me.vasujain.hotel.UserService.dto.UserDTO;
import me.vasujain.hotel.UserService.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(UserDTO user);

    User getUserById(UUID userId);

    User updateUser(UUID userId, UserDTO user);

    void deleteUser(UUID userId);

    User getUserByEmail(String email);

    Object getAllUsers(boolean paginate, Pageable pageable);

}
