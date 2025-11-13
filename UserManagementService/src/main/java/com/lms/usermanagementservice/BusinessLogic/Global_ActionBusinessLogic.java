package com.lms.usermanagementservice.BusinessLogic;

import com.LMS.Constants.ACTIONS;
import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.Exceptions.ActionsService.ActionsAlreadyExistsException;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.LMS.Exceptions.ActionsService.InvalidActionException;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import com.lms.usermanagementservice.Repository.Global.Global_ActionsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class Global_ActionBusinessLogic {


    public Global_ActionBusinessLogic(Global_ActionsRepository actionsRepository)
    {
        this.actionsRepository = actionsRepository;
        TenantContext.setCurrentTenant(GlobalConstant.GLOBAL_METADATA);
    }

    private final Global_ActionsRepository actionsRepository;

    public Global_Actions addAction(ActionRequestDTO actionRequestDTO) {

        try {
            ACTIONS action = ACTIONS.valueOf(actionRequestDTO.actionName().trim().toUpperCase());
            if(actionsRepository.findActionsByAction(action).isPresent())
            {
                throw  new ActionsAlreadyExistsException("Action already exists");
            }
            Global_Actions actions = Global_Actions.builder()
                    .action(action)
                    .build();
            return actionsRepository.save(actions);
        }catch(IllegalArgumentException exception)
        {
            throw new InvalidActionException(exception.getMessage());
        }
    }

    public Page<Global_Actions> findAllActions(Pageable pageable) {
        return actionsRepository.findAll(pageable);
    }

    public Global_Actions findActionById(Long id) {
        return actionsRepository.findById(id).orElseThrow(
                () -> new ActionsNotFoundException("Actions Not Found")
        );
    }

}
