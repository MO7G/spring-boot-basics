package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UpdateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.exceptions.user.EmailAlreadyExistsException;
import com.hajji.springbootbasics.exceptions.user.UserNotFoundException;
import com.hajji.springbootbasics.mapper.UserMapper;
import com.hajji.springbootbasics.model.User;
import com.hajji.springbootbasics.repository.UserRepository;
import com.hajji.springbootbasics.utility.PaginationLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Get all users
    public List<UserResponseDTO> getAllUsers(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(size,page);
        PaginationLogger.logPageFetch("users",pageable);
        Page<User> users = userRepository.findAll(pageable);
        List<UserResponseDTO> userResponseDTOS =users
                .stream()
                .map(user-> UserMapper.toResponseDTO(user))
                .collect(Collectors.toList());

       return userResponseDTOS;
    }

    // Create a new user
    public UserResponseDTO createUser(CreateUserRequestDTO dto) {

        // check first if the email is in our database or not !!
        if(userRepository.existsByEmail(dto.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists :  " + dto.getEmail());
        }
        // simulate password hashing here
        dto.setPassword("HASHED_" + dto.getPassword());
        User user = UserMapper.toEntity(dto);
        log.info("this is the user {}", user.toString());
        User saved = userRepository.save(user);
        return UserMapper.toResponseDTO(saved);
    }


    @Transactional
    public UserResponseDTO applyGeneralUpdates(UpdateUserRequestDTO dto) {
        if (!dto.getUserId().isProvided()) {
            throw new IllegalArgumentException("User ID is required for update");
        }

        User user = userRepository.findById(dto.getUserId().get())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID " + dto.getUserId().get()));

        dto.getFirstName().ifProvided(user::setFirstName);
        dto.getLastName().ifProvided(user::setLastName);
        dto.getIsActive().ifProvided(user::setActive);

        user.setModifiedAt(LocalDateTime.now());

        User updated = userRepository.save(user);
        return UserMapper.toResponseDTO(updated);
    }



    public void deleteUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID " + id));

        log.info("Deleting user with ID {}", id);
        userRepository.delete(user);
    }


    @Transactional
    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }


}
