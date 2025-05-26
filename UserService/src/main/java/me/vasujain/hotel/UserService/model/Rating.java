package me.vasujain.hotel.UserService.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    private String ratingId;
    private UUID userId;
    private UUID hotelId;
    private int rating;
    private String feedback;
    private Hotel hotel;
}
