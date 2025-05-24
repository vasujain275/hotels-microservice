package me.vasujain.hotel.HotelService.repository;

import me.vasujain.hotel.HotelService.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {
}
