package me.vasujain.hotel.HotelService.service.impl;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.HotelService.dto.HotelDTO;
import me.vasujain.hotel.HotelService.exception.ResourceNotFoundException;
import me.vasujain.hotel.HotelService.model.Hotel;
import me.vasujain.hotel.HotelService.repository.HotelRepository;
import me.vasujain.hotel.HotelService.service.HotelService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class HotelServiceImpl implements HotelService {

    private HotelRepository hotelRepository;

    @Override
    public Hotel createHotel(HotelDTO hotelDTO) {

        Hotel newHotel = Hotel.builder()
                .name(hotelDTO.getName())
                .location(hotelDTO.getLocation())
                .about(hotelDTO.getAbout())
                .build();

        return hotelRepository.save(newHotel);
    }

    @Override
    public Hotel getHotelById(UUID hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));
    }

    @Override
    public Hotel updateHotel(UUID hotelId, HotelDTO hotelDTO) {
        Hotel existingHotel = getHotelById(hotelId);

        if (hotelDTO.getName() != null) existingHotel.setName(hotelDTO.getName());
        if (hotelDTO.getLocation() != null) existingHotel.setLocation(hotelDTO.getLocation());
        if (hotelDTO.getAbout() != null) existingHotel.setAbout(hotelDTO.getAbout());

        return hotelRepository.save(existingHotel);
    }

    @Override
    public void deleteHotel(UUID hotelId) {
        Hotel existingHotel = getHotelById(hotelId);
        hotelRepository.delete(existingHotel);
    }

    @Override
    public Object getAllHotels(boolean paginate, Pageable pageable) {
        if (paginate) {
            return hotelRepository.findAll(pageable);
        } else {
            return hotelRepository.findAll();
        }
    }
}
