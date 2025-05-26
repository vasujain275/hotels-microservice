package me.vasujain.hotel.RatingService.service;

import me.vasujain.hotel.RatingService.model.Rating;

import java.util.List;
import java.util.UUID;

public interface RatingService {

    // Create a new rating
    Rating createRating(Rating rating);

    // Get all ratings for a user
    List<Rating> getRatingsByUserId(UUID userId);

    // Get all ratings for a hotel
    List<Rating> getRatingsByHotelId(UUID hotelId);

    // Get all Ratings
    List<Rating> getAllRatings();

    // Get a rating by ID
    Rating getRatingById(String ratingId);

    void deleteRating(String ratingId);
}
