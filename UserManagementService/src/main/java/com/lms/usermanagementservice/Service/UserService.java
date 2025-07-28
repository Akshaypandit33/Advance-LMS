package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.usermanagementservice.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(UserRequestDTO userRequestDTO,String email);
    Page<UserResponseDTO> findAllUsers(Pageable pageable);
    Page<UserResponseDTO> searchUsers(Pageable pageable, String query);
    UserResponseDTO findUserById(UUID id);
    UserResponseDTO findUserByEmail(String email);
    UserResponseDTO changeAccountStatus(UUID userId, String status);
    void deleteUser(UUID userId);
}
