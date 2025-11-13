package com.lms.usermanagementservice.Controller;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.lms.tenantcore.TenantContext;
import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import com.lms.usermanagementservice.Service.Global_ActionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/v1/actions")
public class ActionController {

    private final Global_ActionService globalActionService;

    public ActionController(Global_ActionService globalActionService) {
        this.globalActionService = globalActionService;
        TenantContext.setCurrentTenant(GlobalConstant.GLOBAL_METADATA);
    }

    // GET /v1/actions
    @GetMapping
    public ResponseEntity<Page<Global_Actions>> getAllActions(Pageable pageable) {
        Page<Global_Actions> page = globalActionService.getAllActions(pageable);
        return ResponseEntity.ok(page);
    }

    // GET /v1/actions/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ActionResponseDTO> getActionById(@PathVariable Long id) {
        ActionResponseDTO response = globalActionService.findActionById(id);
        return ResponseEntity.ok(response);
    }
}

