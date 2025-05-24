package me.vasujain.hotel.HotelService.service;

import me.vasujain.hotel.HotelService.dto.HotelDTO;
import me.vasujain.hotel.HotelService.model.Hotel;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface HotelService {

    Hotel createHotel(HotelDTO hotelDTO);

    Hotel getHotelById(UUID hotelId);

    Hotel updateHotel(UUID hotelId, HotelDTO hotelDTO);

    void deleteHotel(UUID hotelId);

    Object getAllHotels(boolean paginate, Pageable pageable);

}
