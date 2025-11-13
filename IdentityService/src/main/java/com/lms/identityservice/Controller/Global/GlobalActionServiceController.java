package com.lms.identityservice.Controller.Global;

import com.LMS.Constants.GlobalConstant;
import com.LMS.DTOs.ActionsDTO.ActionRequestDTO;
import com.LMS.DTOs.ActionsDTO.ActionResponseDTO;
import com.LMS.DTOs.DeleteResourceDTO;
import com.lms.identityservice.Mapper.ActionMapper;
import com.lms.identityservice.Model.Global.GlobalActions;
import com.lms.identityservice.Service.Global.GlobalActionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/global/actions")
@Tag(name = "Global Actions API", description = "APIs to manage global actions in the system")
public class GlobalActionServiceController {

    private final GlobalActionService globalActionsService;
    private final String header = GlobalConstant.HEADER_TENANT_ID;

    @Operation(summary = "Create a new global action")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Action created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<ActionResponseDTO> createAction(@RequestBody @Valid ActionRequestDTO requestDTO) {
        GlobalActions action = ActionMapper.toGlobal_Actions(requestDTO);

        GlobalActions savedAction = globalActionsService.createAction(action);
        return new ResponseEntity<>(ActionMapper.toActionResponseDTO(savedAction), HttpStatus.CREATED);
    }


    @Operation(summary = "Get global action by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ActionResponseDTO> getActionById(
            @Parameter(description = "ID of the action to retrieve") @PathVariable UUID id) {

        GlobalActions action = globalActionsService.getActionById(id);
        if (action == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(ActionMapper.toActionResponseDTO(action));

    }


    @Operation(summary = "Get global action by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<ActionResponseDTO> getActionByName(
            @Parameter(description = "Name of the action to retrieve") @PathVariable String name)
    {
        GlobalActions action = globalActionsService.getActionByName(name);
        if(action == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(ActionMapper.toActionResponseDTO(action), HttpStatus.OK);
    }

    @Operation(summary = "Get all global actions")
    @ApiResponse(responseCode = "200", description = "List of actions retrieved successfully")
    @GetMapping
    public ResponseEntity<List<ActionResponseDTO>> getAllActions(@RequestHeader(header) String header ) {
        List<ActionResponseDTO> actions = globalActionsService.getAllActions()
                .stream()
                .map(ActionMapper::toActionResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(actions);
    }


    @Operation(summary = "Update an existing global action by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action updated successfully"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ActionResponseDTO> updateAction(
            @Parameter(description = "ID of the action to update") @PathVariable UUID id,
            @RequestBody @Valid ActionRequestDTO requestDTO) {

        GlobalActions updatedAction = ActionMapper.toGlobal_Actions(requestDTO);

        try {
            GlobalActions savedAction = globalActionsService.updateAction(id, updatedAction);
            return ResponseEntity.ok(ActionMapper.toActionResponseDTO(savedAction));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Delete a global action by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Action deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Action not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteResourceDTO> deleteAction(
            @Parameter(description = "ID of the action to delete") @PathVariable UUID id) {
        try {
            globalActionsService.deleteAction(id);
            return ResponseEntity.ok(new DeleteResourceDTO("DELETED","Successfully deleted"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
