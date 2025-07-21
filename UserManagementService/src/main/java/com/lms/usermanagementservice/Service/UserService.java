package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);


    Page<UserResponseDTO> getAllUsers(Pageable pageable);
}
