package com.lms.usermanagementservice.Service.ServiceImpl;

import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import com.lms.usermanagementservice.Repository.Global.Global_ActionsRepository;
import com.lms.usermanagementservice.Service.Global_ActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Global_ActionServiceImpl implements Global_ActionService {
    private final Global_ActionsRepository actionsRepository;


    @Override
    public Page<Global_Actions> getAllActions(Pageable pageable) {
        return actionsRepository.findAll(pageable);
    }
}
