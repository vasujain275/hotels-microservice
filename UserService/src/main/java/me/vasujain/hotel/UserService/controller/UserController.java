package me.vasujain.hotel.UserService.controller;

import lombok.AllArgsConstructor;
import me.vasujain.hotel.UserService.dto.UserDTO;
import me.vasujain.hotel.UserService.model.User;
import me.vasujain.hotel.UserService.response.ApiResponse;
import me.vasujain.hotel.UserService.service.UserService;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private Logger logger;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers(
            @RequestParam(defaultValue = "false") boolean paginate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        logger.debug("Fetching Users with pagination={} page={} size={}",paginate, page,size);

        Object result = userService.getAllUsers(paginate, PageRequest.of(page,size));

        if(result instanceof Page){
            Page<User> userPage = (Page<User>) result;
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
            List<User> users = (List<User>) result;
            return ResponseEntity.ok(ApiResponse.builder()
                    .status(HttpStatus.OK)
                    .data(users)
                    .build()
            );
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable String id) {
        logger.debug("Fetching User with ID: {}", id);
        User user = userService.getUserById(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .status(HttpStatus.OK)
                .data(user)
                .build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<User>> getUserByEmail(@PathVariable String email) {
        logger.debug("Fetching User with Email: {}", email);
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(ApiResponse.<User>builder()
                .status(HttpStatus.OK)
                .data(user)
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody UserDTO userDTO) {
        logger.debug("Creating User with data: {}", userDTO);


        User createdUser = userService.createUser(userDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<User>builder()
                .status(HttpStatus.CREATED)
                .data(createdUser)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        logger.debug("Deleting User with ID: {}", id);
        userService.deleteUser(UUID.fromString(id));
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .status(HttpStatus.OK)
                .message("User deleted successfully")
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        logger.debug("Updating User with ID: {} and data: {}", id, userDTO);

        User updatedUser = userService.updateUser(UUID.fromString(id), userDTO);

        return ResponseEntity.ok(ApiResponse.<User>builder()
                .status(HttpStatus.OK)
                .data(updatedUser)
                .build());
    }

}
