package me.vasujain.hotel.UserService.external.service;

import me.vasujain.hotel.UserService.model.Hotel;
import me.vasujain.hotel.UserService.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "HOTEL-SERVICE")
public interface HotelService {

    @GetMapping("/api/v1/hotels/{hotelId}")
    ApiResponse<Hotel> getHotel(@PathVariable("hotelId") UUID hotelId);
}