package com.hajji.springbootbasics.service;

import com.hajji.springbootbasics.dto.user.CreateUserRequestDTO;
import com.hajji.springbootbasics.dto.user.UserResponseDTO;
import com.hajji.springbootbasics.exceptions.user.UserNotFoundException;
import com.hajji.springbootbasics.mapper.UserMapper;
import com.hajji.springbootbasics.model.User;
import com.hajji.springbootbasics.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class UserServiceTest {


    private UserRepository userRepository;
    private UserService userService;

    // runs before every single test method in the whole class
    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class); // mock dependency
        userService = new UserService(userRepository); // inject into service
    }

    @Test
    void getAllUsers_shouldReturnMappedUsers_withPagination() {
        // Arrange
        User user1 = new User();
        user1.setUserId(1);
        user1.setFirstName("John");
        user1.setLastName("Doe");

        User user2 = new User();
        user2.setUserId(2);
        user2.setFirstName("Jane");
        user2.setLastName("Smith");

        Pageable pageable = PageRequest.of(0, 10); // default page and size
        Page<User> userPage = new PageImpl<>(Arrays.asList(user1, user2), pageable, 2);

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        // Act
        List<UserResponseDTO> result = userService.getAllUsers(0, 10);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFirstName()).isEqualTo("John");
        assertThat(result.get(1).getFirstName()).isEqualTo("Jane");
        verify(userRepository, times(1)).findAll(pageable);
    }


    @Test
    void createUser_shouldSaveAndReturnResponseDTO() {
        // Arrange
        CreateUserRequestDTO dto = new CreateUserRequestDTO();
        dto.setFirstName("Ali");
        dto.setLastName("Hajji");
        dto.setEmail("ali@example.com");

        User savedUser = UserMapper.toEntity(dto);
        savedUser.setUserId(100);
        savedUser.setCreatedAt(LocalDateTime.now());
        savedUser.setModifiedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserResponseDTO response = userService.createUser(dto);

        // Assert
        assertThat(response.getUserId()).isEqualTo(100);
        assertThat(response.getFirstName()).isEqualTo("Ali");
        verify(userRepository, times(1)).save(any(User.class));

        // Capture what was passed into repository
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertThat(captor.getValue().getCreatedAt()).isNotNull();
    }

    @Test
    void deleteUser_shouldDeleteWhenUserExists() {
        // Arrange
        Integer id = 1;
        when(userRepository.existsById(id)).thenReturn(true);

        // Act
        userService.deleteUser(id);

        // Assert
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteUser_shouldThrowExceptionWhenUserDoesNotExist() {
        // Arrange
        Integer id = 99;
        when(userRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> userService.deleteUser(id))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User not found with ID " + id);
    }
}
