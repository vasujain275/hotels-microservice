package me.vasujain.hotel.RatingService.controller;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.RatingService.model.Rating;
import me.vasujain.hotel.RatingService.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ratings")
@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(UUID userId) {
        List<Rating> ratings = ratingService.getRatingsByUserId(userId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(UUID hotelId) {
        List<Rating> ratings = ratingService.getRatingsByHotelId(hotelId);
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/{ratingId}")
    public ResponseEntity<Rating> getRatingById(String ratingId) {
        Rating rating = ratingService.getRatingById(ratingId);
        return ResponseEntity.ok(rating);
    }

    

}
