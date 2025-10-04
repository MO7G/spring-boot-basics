package com.hajji.springbootbasics.mapper;

import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UpdateUserRequestDTO;
import com.hajji.springbootbasics.model.User;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;

public class UserMapper {


    // DTO → Entity
    // Update User DTO → Entity (PatchField aware)
    public static void updateEntity(User user, UpdateUserRequestDTO dto) {
        if (user == null || dto == null) return;

        // using method reference instead of lambdas because its more boilerplate code !!
        dto.getFirstName().ifProvided(user::setFirstName); //  same as this -> dto.getFirstName().ifProvided(value -> user.setFirstName(value));
        dto.getLastName().ifProvided(user::setLastName);
        dto.getIsActive().ifProvided(user::setActive);
        user.setModifiedAt(java.time.LocalDateTime.now());
    }


    public static User toEntity(CreateUserRequestDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(dto.getPassword());
        user.setActive(dto.getIsActive());
        return user;
    }




    // Entity → Response DTO
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setModifiedAt(user.getModifiedAt());
        return dto;
    }
}
