package me.vasujain.hotel.RatingService.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("user_ratings")
public class Rating {
    @Id
    private String ratingId;
    private UUID userId;
    private UUID hotelId;
    private int rating;
    private String feedback;
}
