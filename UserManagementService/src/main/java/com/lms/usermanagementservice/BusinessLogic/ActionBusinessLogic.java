package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Constants.ACTIONS;
import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.Exceptions.ActionsService.ActionsAlreadyExistsException;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.LMS.Exceptions.ActionsService.InvalidActionException;
import com.lms.usermanagementservice.Model.Actions;
import com.lms.usermanagementservice.Repository.ActionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActionBusinessLogic {

    private final ActionsRepository actionsRepository;

    public Actions addAction(ActionRequestDTO actionRequestDTO) {

        try {
            ACTIONS action = ACTIONS.valueOf(actionRequestDTO.actionName().trim().toUpperCase());
            if(actionsRepository.findActionsByAction(action).isPresent())
            {
                throw  new ActionsAlreadyExistsException("Action already exists");
            }
            Actions actions = Actions.builder()
                    .action(action)
                    .descriptions(actionRequestDTO.descriptions())
                    .build();
            return actionsRepository.save(actions);
        }catch(IllegalArgumentException exception)
        {
            throw new InvalidActionException(exception.getMessage());
        }
    }

    public List<Actions> findAllActions() {
        return actionsRepository.findAll();
    }

    public Actions findActionById(Long id) {
        return actionsRepository.findById(id).orElseThrow(
                () -> new ActionsNotFoundException("Actions Not Found")
        );
    }
}
