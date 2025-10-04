package com.hajji.springbootbasics.controller;

import com.hajji.springbootbasics.dto.response.ApiResponse;
import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UpdateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.dto.validation.user.UpdateUser;
import com.hajji.springbootbasics.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ Get All Users
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<UserResponseDTO> users = userService.getAllUsers(page, size);
        ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Users fetched successfully",
                users
        );
        return ResponseEntity.ok(response);
    }


    // ✅ Create User
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createUser(
            @Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {
        UserResponseDTO createdUser = userService.createUser(createUserRequestDTO);
        ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "User created successfully",
                createdUser
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/applyGeneralUpdates")
    public ResponseEntity<ApiResponse<UserResponseDTO>> applyGeneralUpdates(
           @Valid @RequestBody UpdateUserRequestDTO updateUserRequestDTO) {

        UserResponseDTO updatedUser = userService.applyGeneralUpdates(updateUserRequestDTO);

        ApiResponse<UserResponseDTO> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                updatedUser
        );

        return ResponseEntity.ok(response);
    }

    // ✅ Delete User
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        ApiResponse<Void> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null
        );
        return ResponseEntity.ok(response);
    }
}
