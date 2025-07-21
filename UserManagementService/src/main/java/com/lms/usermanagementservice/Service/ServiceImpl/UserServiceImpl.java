package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.usermanagementservice.BusinessLogic.UserBusinessLogic;
import com.lms.usermanagementservice.BusinessLogic.UserRoleBusinessLogic;
import com.lms.usermanagementservice.Mapper.UserEntityToResponseDTO;
import com.lms.usermanagementservice.Model.Users;
import com.lms.usermanagementservice.Service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserBusinessLogic userBusinessLogic;
    private final UserRoleBusinessLogic userRoleBusinessLogic;
    private final UserEntityToResponseDTO userEntityToResponseDTO;
    @Transactional
    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {

        // creating user
        Users user = userBusinessLogic.createUser(userRequestDTO);

        // assigning role to user if the list of Roles is not empty
        userRoleBusinessLogic.assignMultipleRoleToUser(
                user.getId(),
                userRequestDTO.rolesId()

        );
        return userEntityToResponseDTO.apply(userBusinessLogic.findUserById(user.getId()));
    }


    @Override

    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<Users> userPage = userBusinessLogic.findAllUsers(pageable);
        return userPage.map(userEntityToResponseDTO);

    }
}
