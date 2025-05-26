package me.vasujain.hotel.RatingService.service.impl;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.RatingService.model.Rating;
import me.vasujain.hotel.RatingService.repository.RatingRepository;
import me.vasujain.hotel.RatingService.service.RatingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    @Override
    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getRatingsByUserId(UUID userId) {
        return ratingRepository.findByUserId(userId);
    }

    @Override
    public List<Rating> getRatingsByHotelId(UUID hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }

    @Override
    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating getRatingById(String ratingId) {
        return ratingRepository.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating not found with id: " + ratingId));
    }

    @Override
    public void deleteRating(String ratingId) {
        if (!ratingRepository.existsById(ratingId)) {
            throw new RuntimeException("Rating not found with id: " + ratingId);
        }
        ratingRepository.deleteById(ratingId);
    }
}
