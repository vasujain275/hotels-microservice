package me.vasujain.hotel.HotelService.controller;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.HotelService.dto.HotelDTO;
import me.vasujain.hotel.HotelService.model.Hotel;
import me.vasujain.hotel.HotelService.response.ApiResponse;
import me.vasujain.hotel.HotelService.service.HotelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "false") boolean paginate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Object result = hotelService.getAllHotels(paginate, PageRequest.of(page,size));

        if(result instanceof Page){
            Page<Hotel> userPage = (Page<Hotel>) result;
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .data(userPage.getContent())
                    .pagination(ApiResponse.PaginationMetadata.builder()
                            .totalElements((int) userPage.getTotalElements())
                            .currentPage(userPage.getNumber())
                            .pageSize(userPage.getSize())
                            .totalPages(userPage.getTotalPages())
                            .build()
                    )
                    .build()
            );
        } else {
            List<Hotel> users = (List<Hotel>) result;
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .data(users)
                    .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Hotel>> getHotelById(@PathVariable UUID id) {
        Hotel hotel = hotelService.getHotelById(id);
        return ResponseEntity.ok(ApiResponse.<Hotel>builder()
                .status(HttpStatus.OK)
                .data(hotel)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Hotel>> updateHotel(@PathVariable UUID id, @RequestBody HotelDTO hotel) {
        Hotel updatedHotel = hotelService.updateHotel(id, hotel);
        return ResponseEntity.ok(ApiResponse.<Hotel>builder()
                .status(HttpStatus.OK)
                .data(updatedHotel)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHotel(@PathVariable UUID id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.NO_CONTENT)
                .message("Hotel deleted successfully")
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Hotel>> createHotel(@RequestBody HotelDTO hotel) {
        Hotel newHotel = hotelService.createHotel(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<Hotel>builder()
                .status(HttpStatus.CREATED)
                .data(newHotel)
                .message("Hotel created successfully")
                .build());
    }
}
