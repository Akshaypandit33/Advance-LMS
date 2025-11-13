package com.lms.usermanagementservice.Gloabal_metadata.Seeder;

import com.LMS.Constants.ACTIONS;
import com.LMS.Constants.DefaultRoles;
import com.LMS.Constants.ResourcesName;
import com.lms.usermanagementservice.Model.Globals.*;
import com.lms.usermanagementservice.Repository.Global.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
public class GlobalRolesSeeder {

    private final Global_RolesRepository rolesRepository;
    private final Global_PermissionRepository permissionRepository;
    private final Global_RolePermissionRepository rolePermissionRepository;
    private final Global_ResourceRepository resourceRepository;
    private final Global_ActionsRepository actionsRepository;

    public  void seedData()
    {
        // step -1 add actions
        Map<ACTIONS, Global_Actions> actionsMap = seedActions();

        // step 2 : add resource list in resource repo
        Map<ResourcesName, Global_Resources>  resourcesMap = seedResources();

        // STEP 3: add all permissions
        List<Global_Permissions> permissionList =seedPermissions(actionsMap,resourcesMap);

        // step 4: add all default roles
        Map<String, Global_Roles> rolesMap = seedRoles();

        // Step 5: Assign Permissions to Roles
        // Super Admin → All Permissions
        Global_Roles superAdminRoles = rolesMap.get(DefaultRoles.SUPER_ADMIN.name());

        for(Global_Permissions permission : permissionList) {
            rolePermissionRepository.findByRoleIdAndPermissionId(superAdminRoles.getId(), permission.getId())
                    .orElseGet(
                            () -> rolePermissionRepository.save(
                                    Global_RolePermission.builder()
                                            .permission(permission)
                                            .role(superAdminRoles)
                                            .build()
                            )
                    );
        }

        // Admin → CRUD on USERS, ROLES, COURSE
        assignPermissions(rolesMap.get(DefaultRoles.ADMIN.name()), resourcesMap, actionsMap,
                List.of(ResourcesName.USERS, ResourcesName.ROLES, ResourcesName.COURSE),
                List.of(ACTIONS.CREATE, ACTIONS.READ, ACTIONS.UPDATE, ACTIONS.DELETE));

        // Instructor → CRUD on COURSE, READ USERS
        assignPermissions(rolesMap.get(DefaultRoles.INSTRUCTOR.name()), resourcesMap, actionsMap,
                List.of(ResourcesName.COURSE),
                List.of(ACTIONS.CREATE, ACTIONS.READ, ACTIONS.UPDATE, ACTIONS.DELETE));
        assignPermissions(rolesMap.get(DefaultRoles.INSTRUCTOR.name()), resourcesMap, actionsMap,
                List.of(ResourcesName.USERS),
                List.of(ACTIONS.READ));

        // Student → READ COURSE, ACCESS_OWN
        assignPermissions(rolesMap.get(DefaultRoles.STUDENT.name()), resourcesMap, actionsMap,
                List.of(ResourcesName.COURSE),
                List.of(ACTIONS.READ));
        assignPermissions(rolesMap.get(DefaultRoles.STUDENT.name()), resourcesMap, actionsMap,
                List.of(ResourcesName.USERS),
                List.of(ACTIONS.ACCESS_OWN));

    }



    private Map<ACTIONS, Global_Actions> seedActions() {
        List<ACTIONS> allEnumValues = Arrays.asList(ACTIONS.values());

        // Fetch all existing actions
        List<Global_Actions> existingActions = actionsRepository.findByActionIn((allEnumValues));
        Map<ACTIONS, Global_Actions> existingMap = existingActions.stream()
                .collect(Collectors.toMap(Global_Actions::getAction, Function.identity()));

        // Create new actions only for missing ones
        List<Global_Actions> toCreate = allEnumValues.stream()
                .filter(action -> !existingMap.containsKey(action))
                .map(action -> Global_Actions.builder().action(action).build())
                .collect(Collectors.toList());

        List<Global_Actions> saved = actionsRepository.saveAll(toCreate);

        // Combine and return
        List<Global_Actions> all = new ArrayList<>(existingActions);
        all.addAll(saved);

        return all.stream().collect(Collectors.toMap(Global_Actions::getAction, Function.identity()));
    }



    private Map<ResourcesName, Global_Resources> seedResources() {
        List<ResourcesName> allResources = Arrays.asList(ResourcesName.values());

        // Fetch existing
        List<Global_Resources> existing = resourceRepository.findByResourceNameIn((allResources));
        Map<ResourcesName, Global_Resources> existingMap = existing.stream()
                .collect(Collectors.toMap(Global_Resources::getResourceName, Function.identity()));

        // Build missing
        List<Global_Resources> toCreate = allResources.stream()
                .filter(r -> !existingMap.containsKey(r))
                .map(r -> Global_Resources.builder().resourceName(r).build())
                .collect(Collectors.toList());

        List<Global_Resources> saved = resourceRepository.saveAll(toCreate);

        // Merge
        List<Global_Resources> all = new ArrayList<>(existing);
        all.addAll(saved);

        return all.stream().collect(Collectors.toMap(Global_Resources::getResourceName, Function.identity()));
    }






    private List<Global_Permissions> seedPermissions(Map<ACTIONS, Global_Actions> actionsMap,
                                             Map<ResourcesName, Global_Resources> resourceMap) {

        // Step 1: Fetch all existing permissions in one query
        List<Global_Permissions> existingPermissions = permissionRepository.findAll();
        Set<String> existingKeys = existingPermissions.stream()
                .map(p -> p.getActions().getId() + "_" + p.getResource().getId())
                .collect(Collectors.toSet());

        List<Global_Permissions> toCreate = new ArrayList<>();

        // Step 2: Create missing permissions in memory
        for (Global_Resources resource : resourceMap.values()) {
            for (Global_Actions action : actionsMap.values()) {
                String key = action.getId() + "_" + resource.getId();
                if (!existingKeys.contains(key)) {
                    toCreate.add(
                            Global_Permissions.builder()
                                    .actions(action)
                                    .resource(resource)
                                    .build()
                    );
                }
            }
        }

        // Step 3: Bulk insert missing permissions
        List<Global_Permissions> saved = permissionRepository.saveAll(toCreate);

        // Step 4: Combine and return all permissions
        existingPermissions.addAll(saved);
        return existingPermissions;
    }


    private Map<String, Global_Roles> seedRoles() {

        List<String> defaultRoleNames = Arrays.stream(DefaultRoles.values())
                .map(Enum::name)
                .toList();

        // Step 1: Fetch existing roles from DB
        List<Global_Roles> existingRoles = rolesRepository.findByRoleNameIn(defaultRoleNames);
        Map<String, Global_Roles> existingRolesMap = existingRoles.stream()
                .collect(Collectors.toMap(Global_Roles::getRoleName, Function.identity()));

        // Step 2: Prepare missing roles
        List<Global_Roles> rolesToCreate = defaultRoleNames.stream()
                .filter(roleName -> !existingRolesMap.containsKey(roleName))
                .map(roleName -> Global_Roles.builder()
                        .roleName(roleName)
                        .roleDescription("DEFAULT DESCRIPTION")
                        .build())
                .collect(Collectors.toList());

        // Step 3: Save all missing roles
        List<Global_Roles> savedRoles = rolesRepository.saveAll(rolesToCreate);

        // Step 4: Merge all roles into final map
        List<Global_Roles> allRoles = new ArrayList<>();
        allRoles.addAll(existingRoles);
        allRoles.addAll(savedRoles);

        return allRoles.stream()
                .collect(Collectors.toMap(Global_Roles::getRoleName, Function.identity()));
    }


    private void assignPermissions(Global_Roles role,
                                   Map<ResourcesName, Global_Resources> resourceMap,
                                   Map<ACTIONS, Global_Actions> actionMap,
                                   List<ResourcesName> resources,
                                   List<ACTIONS> actionsList)
    {

        List<Global_RolePermission> toInsert = new ArrayList<>();

        for (ResourcesName resourceName : resources) {
            for (ACTIONS act : actionsList) {
                Global_Resources resource = resourceMap.get(resourceName);
                Global_Actions action = actionMap.get(act);

                permissionRepository.findPermissionByActionsIdAndResourceId(action.getId(), resource.getId())
                        .ifPresent(permission -> {
                            boolean alreadyExists = rolePermissionRepository
                                    .findByRoleIdAndPermissionId(role.getId(), permission.getId()).isPresent();

                            if (!alreadyExists) {
                                toInsert.add(Global_RolePermission.builder()
                                        .role(role)
                                        .permission(permission)
                                        .build());
                            }
                        });
            }
        }

        if (!toInsert.isEmpty()) {
            rolePermissionRepository.saveAll(toInsert); // ✅ Efficient batch insert
        }

    }
}
