package com.lms.identityservice.Seeder;

import com.LMS.Constants.ACTIONS;
import com.LMS.Constants.DefaultRoles;
import com.LMS.Constants.GlobalConstant;
import com.LMS.Constants.ResourcesName;
import com.LMS.DTOs.PermissionDTO.PermissionRequestDTO;
import com.lms.identityservice.Model.Global.*;
import com.lms.identityservice.Service.Global.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GlobalRolePermissionSeeder implements CommandLineRunner {

    private final GlobalActionService globalActionService;
    private final GlobalPermissionService globalPermissionService;
    private final GlobalResourceService globalResourceService;
    private final GlobalRolePermissionsService globalRolePermissionsService;
    private final GlobalRoleService globalRoleService;

    @Override
    public void run(String... args) throws Exception {

        List<GlobalActions> globalActionsList = new ArrayList<>();

        // step 1 inject actions
        for(ACTIONS action : ACTIONS.values()){
            if(globalActionService.getActionByName(action.toString()) != null){
                continue;
            }
            GlobalActions actions = GlobalActions.builder()
                    .name(action.name())
                    .description("Default description")
                    .build();

            GlobalActions savedActions = globalActionService.createAction(actions);
            globalActionsList.add(savedActions);
        }


        // step 2 inject resources
        List<GlobalResources> globalResourceList = new ArrayList<>();
        for(ResourcesName resourcesName : ResourcesName.values()){
            if(globalResourceService.getResourceByName(resourcesName.toString()) != null){
                continue;
            }
            GlobalResources resources = GlobalResources.builder()
                    .name(resourcesName.name())
                    .description("Default description")
                    .resourceType("Default")
                    .build();
            GlobalResources savedResource = globalResourceService.createResource(resources);
            globalResourceList.add(savedResource);
        }

        List<GlobalPermissions>  globalPermissionsList = new ArrayList<>();
        for(GlobalActions globalAction : globalActionsList)
        {
            for (GlobalResources globalResources : globalResourceList)
            {
                if(globalPermissionService.permissionExistsForActionAndResource(globalAction.getId(), globalResources.getId()))
                {
                    continue;
                }
                GlobalPermissions savedPermission = globalPermissionService.createPermission(
                        new PermissionRequestDTO(
                                globalAction.getId(),
                                globalResources.getId()
                        )
                );
                globalPermissionsList.add(savedPermission);
            }
        }

    }
}


