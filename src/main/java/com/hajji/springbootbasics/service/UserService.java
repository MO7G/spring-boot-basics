package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.mapper.UserMapper;
import com.hajji.springbootbasics.model.User;
import com.hajji.springbootbasics.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Create a new user
    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        User user = UserMapper.toEntity(dto);
        System.out.println("this is the user " + user.toString());
        LocalDateTime now = LocalDateTime.now();
        user.setCreatedAt(now);
        user.setModifiedAt(now);
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    // Update existing user
    @Transactional
    public UserResponseDTO updateUser(UserRequestDTO dto) {
        if (dto.getUserId() == null) {
            throw new IllegalArgumentException("User ID is required for update");
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + dto.getUserId()));

        // Update fields
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setIsActive(dto.getIsActive());
        user.setModifiedAt(LocalDateTime.now());

        User updated = userRepository.save(user);
        return UserMapper.toResponseDTO(updated);
    }

    // Delete user by ID
    @Transactional
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID " + id);
        }
        userRepository.deleteById(id);
    }
}
