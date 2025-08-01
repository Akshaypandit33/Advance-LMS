package com.lms.usermanagementservice.Mapper;

import com.LMS.Constants.AccountStatus;
import com.LMS.DTOs.UserService.UserRequestDTO;
import com.lms.usermanagementservice.Model.Users;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserRequestDTOToEntityMapper  {


    public Users apply(UserRequestDTO userRequestDTO,String encodedPassword) {
        return
                Users.builder()
                        .firstName(userRequestDTO.firstName())
                        .lastName(userRequestDTO.lastName())
                        .password(encodedPassword)
                        .email(userRequestDTO.email())
                        .gender(userRequestDTO.gender())

                        .phoneNumber(userRequestDTO.phoneNumber())
                        .accountStatus(AccountStatus.valueOf(userRequestDTO.accountStatus().toUpperCase()))
                        .build();
    }
}
