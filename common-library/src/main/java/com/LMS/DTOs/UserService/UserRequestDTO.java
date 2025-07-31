package com.LMS.DTOs.UserService;

import java.util.List;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        String phoneNumber,
        String gender,
        String accountStatus,
        List<String> roles
) {

}
