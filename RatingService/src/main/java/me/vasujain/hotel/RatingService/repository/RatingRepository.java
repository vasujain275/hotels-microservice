package me.vasujain.hotel.RatingService.repository;

import me.vasujain.hotel.RatingService.model.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends MongoRepository<Rating, String> {

    List<Rating> findByUserId(UUID userId);
    List<Rating> findByHotelId(UUID hotelId);

}
