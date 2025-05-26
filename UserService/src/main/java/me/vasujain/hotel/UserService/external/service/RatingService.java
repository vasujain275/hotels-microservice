package me.vasujain.hotel.UserService.external.service;

import me.vasujain.hotel.UserService.model.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "RATING-SERVICE")
public interface RatingService {

    @GetMapping("/api/v1/ratings/user/{userId}")
    Rating[] getRatingsByUserId(UUID userId);

    @GetMapping("/api/v1/ratings/user/email/{email}")
    Rating[] getRatingsByUserEmail(String email);

    @PostMapping("/api/v1/ratings")
    Rating createRating(Rating rating);
}
