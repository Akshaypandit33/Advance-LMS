package com.lms.usermanagementservice.Service.ServiceImpl;

import com.LMS.DTOs.UserService.UserRequestDTO;
import com.LMS.DTOs.UserService.UserResponseDTO;
import com.lms.tenantcore.TenantContext;
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

import java.util.Map;
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

        System.out.println("current tenant is"+TenantContext.getCurrentTenant());

        // creating user
        Users user = userBusinessLogic.createUser(userRequestDTO);

        // assigning role to user if the list of Roles is not empty
        userRoleBusinessLogic.assignMultipleRoleToUser(
                user.getId(),
                userRequestDTO.roles()

        );
        return userEntityToResponseDTO.apply(userBusinessLogic.findUserById(user.getId()));
    }

    @Override
    public UserResponseDTO updateUser(UserRequestDTO userRequestDTO, String email) {
        return userEntityToResponseDTO.apply(userBusinessLogic.updateUser(userRequestDTO,email.toLowerCase()));
    }

    @Override
    public Page<UserResponseDTO> findAllUsers(Pageable pageable) {
        Page<Users> userPage = userBusinessLogic.findAllUsers(pageable);
        return userPage.map(userEntityToResponseDTO);
    }

    @Override
    public Page<UserResponseDTO> searchUsers(Pageable pageable, String query) {
        Page<Users> usersPage = userBusinessLogic.searchUsers(pageable,query);
        return usersPage.map(userEntityToResponseDTO);
    }

    @Override
    public UserResponseDTO findUserById(UUID id) {
        return userEntityToResponseDTO.apply(userBusinessLogic.findUserById(id));
    }

    @Override
    public UserResponseDTO findUserByEmail(String email) {
        return userEntityToResponseDTO.apply(userBusinessLogic.getUserByEmail(email));
    }

    @Override
    public UserResponseDTO changeAccountStatus(UUID userId, String status) {
        return userEntityToResponseDTO.apply(userBusinessLogic.changeAccountStatus(userId,status));
    }

    @Override
    public Map<String, String> deleteUser(UUID userId) {
        return userBusinessLogic.deleteUser(userId);

    }



}
