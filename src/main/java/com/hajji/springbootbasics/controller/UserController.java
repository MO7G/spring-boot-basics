package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponseWrapper;
import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UpdateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/user/")
@Tag(
        name = "User Controller",
        description = "Handles user management operations including creation, retrieval, update, and deletion of users."
)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @Operation(
            summary = "Fetch all users with pagination",
            description = "Retrieves a paginated list of users. Default page=0, size=10."
    )
    public ResponseEntity<ApiResponseWrapper<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<UserResponseDTO> users = userService.getAllUsers(page, size);
        ApiResponseWrapper<List<UserResponseDTO>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Users fetched successfully",
                users
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/create")
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user with the provided details."
    )
    public ResponseEntity<ApiResponseWrapper<UserResponseDTO>> createUser(
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(createUserRequestDTO);
        ApiResponseWrapper<UserResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.CREATED.value(),
                "User created successfully",
                createdUser
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @PatchMapping("/update")
    @Operation(
            summary = "Update an existing user",
            description = "Applies partial updates to an existing user based on the provided data."
    )
    public ResponseEntity<ApiResponseWrapper<UserResponseDTO>> applyGeneralUpdates(
            @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {

        UserResponseDTO updatedUser = userService.update(updateUserRequestDTO);

        ApiResponseWrapper<UserResponseDTO> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                updatedUser
        );

        return ResponseEntity.ok(response);
    }



    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes an existing user from the system by their ID."
    )
    public ResponseEntity<ApiResponseWrapper<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}
