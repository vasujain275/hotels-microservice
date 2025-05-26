package me.vasujain.hotel.UserService.service.impl;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.UserService.dto.UserDTO;
import me.vasujain.hotel.UserService.exception.ResourceNotFoundException;
import me.vasujain.hotel.UserService.external.service.HotelService;
import me.vasujain.hotel.UserService.external.service.RatingService;
import me.vasujain.hotel.UserService.model.Hotel;
import me.vasujain.hotel.UserService.model.Rating;
import me.vasujain.hotel.UserService.model.User;
import me.vasujain.hotel.UserService.repository.UserRepository;
import me.vasujain.hotel.UserService.response.ApiResponse;
import me.vasujain.hotel.UserService.service.UserService;
import org.slf4j.Logger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private Logger logger;
    private HotelService hotelService;
    private RatingService ratingService;

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
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        Rating[] ratings = ratingService.getRatingsByUserId(userId);

        if (ratings == null || ratings.length == 0) {
            logger.info("No ratings found for user with ID: {}", userId);
            user.setRatings(new ArrayList<>());
            return user;
        }
        List<Rating> ratingList = Arrays.stream(ratings).toList();

        // Fetch hotel details for each rating
        for (Rating rating : ratingList) {
            try {
                ApiResponse<Hotel> hotelResponse = hotelService.getHotel(rating.getHotelId());

                logger.info("Fetched hotel response: {}", hotelResponse);

                if (hotelResponse != null && hotelResponse.getData() != null) {
                    rating.setHotel(hotelResponse.getData());
                    logger.info("Set hotel for rating: {}", hotelResponse.getData());
                } else {
                    logger.warn("Hotel not found for rating with ID: {}", rating.getRatingId());
                }
            } catch (Exception e) {
                logger.error("Error fetching hotel for rating ID: {}, Error: {}", rating.getRatingId(), e.getMessage());
            }
        }

        // Set the ratings to the user object
        user.setRatings(ratingList);

        return user;
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
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        Rating[] ratings = ratingService.getRatingsByUserEmail(email);

        if (ratings == null || ratings.length == 0) {
            logger.info("No ratings found for email with ID: {}", email);
            user.setRatings(new ArrayList<>());
            return user;
        }
        List<Rating> ratingList = Arrays.stream(ratings).toList();

        // Fetch hotel details for each rating
        for (Rating rating : ratingList) {
            try {
                ApiResponse<Hotel> hotelResponse = hotelService.getHotel(rating.getHotelId());

                logger.info("Fetched hotel response: {}", hotelResponse);

                if (hotelResponse != null && hotelResponse.getData() != null) {
                    rating.setHotel(hotelResponse.getData());
                    logger.info("Set hotel for rating: {}", hotelResponse.getData());
                } else {
                    logger.warn("Hotel not found for rating with ID: {}", rating.getRatingId());
                }
            } catch (Exception e) {
                logger.error("Error fetching hotel for rating ID: {}, Error: {}", rating.getRatingId(), e.getMessage());
            }
        }

        // Set the ratings to the user object
        user.setRatings(ratingList);

        return user;
    }

    @Override
    public Object getAllUsers(boolean paginate, Pageable pageable) {
        // Implement rating fetching logic with pagination support and wihout pagination both
        if (paginate) {
            return userRepository.findAll(pageable);
        } else {
            return userRepository.findAll();
        }
    }
}
