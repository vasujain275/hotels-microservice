package me.vasujain.hotel.UserService.service.impl;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.UserService.dto.UserDTO;
import me.vasujain.hotel.UserService.exception.ResourceNotFoundException;
import me.vasujain.hotel.UserService.model.User;
import me.vasujain.hotel.UserService.repository.UserRepository;
import me.vasujain.hotel.UserService.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(UserDTO user) {

        // Build a new User object from the DTO using the builder pattern
        User newUser = User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .about(user.getAbout())
                .build();

        // Save the new user to the repository
        return userRepository.save(newUser);

    }

    @Override
    public User getUserById(UUID userId) {
        // Fetch the user by ID from the repository
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public User updateUser(UUID userId, UserDTO user) {
        // Fetch the existing user
        User existingUser = getUserById(userId);

        // Update fields if they are not null
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getAbout() != null) {
            existingUser.setAbout(user.getAbout());
        }

        // Save the updated user back to the repository
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID userId) {
        // Check if the user exists before deleting
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with ID: " + userId);
        }
        // Delete the user from the repository
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        // Fetch the user by email from the repository
        return userRepository.findByEmail(email);
    }

    @Override
    public Object getAllUsers(boolean paginate, Pageable pageable) {
        if(paginate){
            return userRepository.findAll(pageable);
        }
        return userRepository.findAll();
    }
}
