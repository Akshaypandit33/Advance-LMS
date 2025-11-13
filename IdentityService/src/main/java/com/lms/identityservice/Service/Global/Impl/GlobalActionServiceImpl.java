package com.lms.identityservice.Service.Global.Impl;

import com.LMS.Exceptions.ActionsService.ActionsAlreadyExistsException;
import com.LMS.Exceptions.ActionsService.ActionsNotFoundException;
import com.lms.identityservice.Model.Global.GlobalActions;
import com.lms.identityservice.Repository.Global.GlobalActionsRepository;
import com.lms.identityservice.Service.Global.GlobalActionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GlobalActionServiceImpl implements GlobalActionService {

    private final GlobalActionsRepository globalActionsRepository;

    @Transactional
    @Override
    public GlobalActions createAction(GlobalActions action) {
        if(!action.getName().isEmpty())
        {
            if(getActionByName(action.getName().toUpperCase()) != null)
            {
                throw new ActionsAlreadyExistsException(action.getName());
            }
        }
        return globalActionsRepository.save(action);
    }

    @Override
    public GlobalActions getActionById(UUID id) {
        return globalActionsRepository.findById(id).orElseThrow(
                () -> new ActionsNotFoundException("Action with id " + id + " not found")
        );
    }

    @Override
    public GlobalActions getActionByName(String name) {
        return globalActionsRepository.findByNameIgnoreCase(name).orElseThrow(
                () -> new ActionsNotFoundException("Action with name " + name + " not found")
        );
    }

    @Override
    public List<GlobalActions> getAllActions() {
        return globalActionsRepository.findAll();
    }

    @Transactional
    @Override
    public GlobalActions updateAction(UUID id, GlobalActions updatedAction) {
        GlobalActions global_Actions = getActionById(id);
        if(updatedAction.getName() != null && !updatedAction.getName().isEmpty()) {
            global_Actions.setName(updatedAction.getName());
        }
        if(updatedAction.getDescription() != null && !updatedAction.getDescription().isEmpty()) {
            global_Actions.setDescription(updatedAction.getDescription());
        }
        return globalActionsRepository.save(global_Actions);

    }

    @Transactional
    @Override
    public void deleteAction(UUID id) {
        GlobalActions global_Actions = getActionById(id);
        globalActionsRepository.delete(global_Actions);
    }

}
