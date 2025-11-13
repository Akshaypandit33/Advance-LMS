package com.lms.identityservice.Mapper;

import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.lms.identityservice.Model.Global.GlobalActions;

public class ActionMapper {

    public static ActionResponseDTO toActionResponseDTO(GlobalActions action) {
        return new ActionResponseDTO(action.getId(), action.getName(), action.getDescription(), action.getCreatedAt(), action.getUpdatedAt());
    }

    public static GlobalActions toGlobal_Actions(ActionRequestDTO action) {
        return GlobalActions.builder()
                .name(action.actionName())
                .description(action.descriptions())
                .build();
    }
}
