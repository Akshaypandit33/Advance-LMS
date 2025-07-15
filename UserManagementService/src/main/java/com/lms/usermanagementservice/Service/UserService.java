package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}
