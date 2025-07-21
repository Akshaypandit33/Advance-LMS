package com.lms.usermanagementservice.Mapper;

import com.LMS.Constants.ACTIONS;
import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.lms.usermanagementservice.Model.Actions;

public class ActionsMapper {

    public static ActionResponseDTO toResponseDTO(Actions actions) {
        return new ActionResponseDTO(
                actions.getId(),
                actions.getActionString(),
                actions.getDescriptions()
        );
    }

    public static Actions toModel(ActionRequestDTO actionRequestDTO) {
        return Actions.builder()
                .action(ACTIONS.valueOf(actionRequestDTO.actionName().toUpperCase()))
                .build();
    }
}
