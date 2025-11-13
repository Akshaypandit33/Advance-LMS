package com.lms.identityservice.Service.Global;

import com.lms.identityservice.Model.Global.GlobalActions;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GlobalActionService {
    GlobalActions createAction(GlobalActions action);
    GlobalActions getActionById(UUID id);
    GlobalActions getActionByName(String name);
    List<GlobalActions> getAllActions();
    GlobalActions updateAction(UUID id, GlobalActions updatedAction);
    void deleteAction(UUID id);
}


