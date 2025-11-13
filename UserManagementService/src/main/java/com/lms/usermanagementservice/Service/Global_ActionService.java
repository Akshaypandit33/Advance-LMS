package com.lms.usermanagementservice.Service;

import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Global_ActionService {

    Page<Global_Actions> getAllActions(Pageable pageable);

    ActionResponseDTO findActionById(Long id);
}
