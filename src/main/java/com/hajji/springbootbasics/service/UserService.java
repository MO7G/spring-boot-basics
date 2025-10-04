package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UpdateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.exceptions.user.EmailAlreadyExistsException;
import com.hajji.springbootbasics.exceptions.user.UserNotFoundException;
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
       List<UserResponseDTO> allusers =  userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDTO)
                .collect(Collectors.toList());


       return allusers;
    }

    // Create a new user
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {
        // check first if the email is in our database or not !!
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists :  " + dto.getEmail());
        }
        // simulate hashing by prefixing with "HASHED_"
        dto.setPassword("HASHED_" + dto.getPassword());
        User user = UserMapper.toEntity(dto);
        System.out.println("this is the user " + user.toString());
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }

    @Transactional
    public UserResponseDTO createTwoUsersWithFailure(CreateUserRequestDTO dto1, CreateUserRequestDTO dto2) {
        // First user
        dto1.setPassword("HASHED_" + dto1.getPassword());
        User user1 = UserMapper.toEntity(dto1);
        userRepository.save(user1);

        // Simulate failure before second user is saved
        if (true) {
            throw new RuntimeException("Simulated failure!");
        }

        // Second user (won't be executed)
        dto2.setPassword("HASHED_" + dto2.getPassword());
        dto2.setEmail("extra" + dto2.getEmail());
        User user2 = UserMapper.toEntity(dto2);
        userRepository.save(user2);
        return UserMapper.toResponseDTO(user2);
    }


    // Update existing user
//    @Transactional
//    public UserResponseDTO updateUser(UpdateUserRequestDTO dto) {
//        if (dto.getUserId() == null) {
//            throw new IllegalArgumentException("User ID is required for update");
//        }
//
//        User user = userRepository.findById(dto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + dto.getUserId()));
//
//        // Update fields
//        user.setFirstName(dto.getFirstName());
//        user.setLastName(dto.getLastName());
//        user.setEmail(dto.getEmail());
//        user.setIsActive(dto.getIsActive());
//        user.setModifiedAt(LocalDateTime.now());
//
//        User updated = userRepository.save(user);
//        return UserMapper.toResponseDTO(updated);
//    }

    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID " + id);
        }
        userRepository.deleteById(id);
    }


    @Transactional
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }


}
