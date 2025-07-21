package com.lms.usermanagementservice.Seeder;

import com.LMS.Constants.ACTIONS;
import com.LMS.Constants.DefaultRoles;

import com.LMS.Constants.GlobalConstant;
import com.LMS.Constants.ResourcesName;
import com.lms.usermanagementservice.Model.Actions;
import com.lms.usermanagementservice.Model.Permission;
import com.lms.usermanagementservice.Model.Resource;
import com.lms.usermanagementservice.Model.Roles;
import com.lms.usermanagementservice.Model.RolePermission;

import com.lms.usermanagementservice.Repository.ActionsRepository;
import com.lms.usermanagementservice.Repository.ResourceRepository;
import com.lms.usermanagementservice.Repository.RolePermissionRepository;
import com.lms.usermanagementservice.Repository.RolesRepository;
import com.lms.usermanagementservice.Repository.PermissionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;



@Component
@Order(1)

@RequiredArgsConstructor
public class RolesPermissionSeeder implements CommandLineRunner {
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final ResourceRepository resourceRepository;
    private final ActionsRepository actionsRepository;


    @Transactional
    @Override
    public void run(String... args) throws Exception {

        // step -1 add actions
        Map<ACTIONS, Actions> actionsMap = seedActions();

        // step 2 : add resource list in resource repo
        Map<ResourcesName, Resource>  resourcesMap = seedResources();

        // STEP 3: add all permissions
        List<Permission> permissionList =seedPermissions(actionsMap,resourcesMap);

        // step 4: add all default roles
        Map<String, Roles> rolesMap = seedRoles();

        // Step 5: Assign Permissions to Roles
        // Super Admin → All Permissions
        Roles superAdminRoles = rolesMap.get(DefaultRoles.SUPER_ADMIN.name());

        for(Permission permission : permissionList) {
            rolePermissionRepository.findByRoleIdAndPermissionId(superAdminRoles.getId(), permission.getId())
                    .orElseGet(
                            () -> rolePermissionRepository.save(
                                    RolePermission.builder()
                                            .permission(permission)
                                            .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
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


    private Map<ACTIONS, Actions> seedActions() {
        List<ACTIONS> allEnumValues = Arrays.asList(ACTIONS.values());

        // Fetch all existing actions
        List<Actions> existingActions = actionsRepository.findByActionIn((allEnumValues));
        Map<ACTIONS, Actions> existingMap = existingActions.stream()
                .collect(Collectors.toMap(Actions::getAction, Function.identity()));

        // Create new actions only for missing ones
        List<Actions> toCreate = allEnumValues.stream()
                .filter(action -> !existingMap.containsKey(action))
                .map(action -> Actions.builder().action(action).build())
                .collect(Collectors.toList());

        List<Actions> saved = actionsRepository.saveAll(toCreate);

        // Combine and return
        List<Actions> all = new ArrayList<>(existingActions);
        all.addAll(saved);

        return all.stream().collect(Collectors.toMap(Actions::getAction, Function.identity()));
    }



    private Map<ResourcesName, Resource> seedResources() {
        List<ResourcesName> allResources = Arrays.asList(ResourcesName.values());

        // Fetch existing
        List<Resource> existing = resourceRepository.findByResourceNameIn((allResources));
        Map<ResourcesName, Resource> existingMap = existing.stream()
                .collect(Collectors.toMap(Resource::getResourceName, Function.identity()));

        // Build missing
        List<Resource> toCreate = allResources.stream()
                .filter(r -> !existingMap.containsKey(r))
                .map(r -> Resource.builder().resourceName(r).build())
                .collect(Collectors.toList());

        List<Resource> saved = resourceRepository.saveAll(toCreate);

        // Merge
        List<Resource> all = new ArrayList<>(existing);
        all.addAll(saved);

        return all.stream().collect(Collectors.toMap(Resource::getResourceName, Function.identity()));
    }






    private List<Permission> seedPermissions(Map<ACTIONS, Actions> actionsMap,
                                             Map<ResourcesName, Resource> resourceMap) {

        // Step 1: Fetch all existing permissions in one query
        List<Permission> existingPermissions = permissionRepository.findAll();
        Set<String> existingKeys = existingPermissions.stream()
                .map(p -> p.getActions().getId() + "_" + p.getResource().getId())
                .collect(Collectors.toSet());

        List<Permission> toCreate = new ArrayList<>();

        // Step 2: Create missing permissions in memory
        for (Resource resource : resourceMap.values()) {
            for (Actions action : actionsMap.values()) {
                String key = action.getId() + "_" + resource.getId();
                if (!existingKeys.contains(key)) {
                    toCreate.add(
                            Permission.builder()
                                    .actions(action)
                                    .resource(resource)
                                    .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
                                    .build()
                    );
                }
            }
        }

        // Step 3: Bulk insert missing permissions
        List<Permission> saved = permissionRepository.saveAll(toCreate);

        // Step 4: Combine and return all permissions
        existingPermissions.addAll(saved);
        return existingPermissions;
    }


    private Map<String, Roles> seedRoles() {

        List<String> defaultRoleNames = Arrays.stream(DefaultRoles.values())
                .map(Enum::name)
                .toList();

        // Step 1: Fetch existing roles from DB
        List<Roles> existingRoles = rolesRepository.findByRoleNameIn(defaultRoleNames);
        Map<String, Roles> existingRolesMap = existingRoles.stream()
                .collect(Collectors.toMap(Roles::getRoleName, Function.identity()));

        // Step 2: Prepare missing roles
        List<Roles> rolesToCreate = defaultRoleNames.stream()
                .filter(roleName -> !existingRolesMap.containsKey(roleName))
                .map(roleName -> Roles.builder()
                        .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
                        .roleName(roleName)
                        .roleDescription("DEFAULT DESCRIPTION")
                        .isSystemDefined(Boolean.TRUE)
                        .build())
                .collect(Collectors.toList());

        // Step 3: Save all missing roles
        List<Roles> savedRoles = rolesRepository.saveAll(rolesToCreate);

        // Step 4: Merge all roles into final map
        List<Roles> allRoles = new ArrayList<>();
        allRoles.addAll(existingRoles);
        allRoles.addAll(savedRoles);

        return allRoles.stream()
                .collect(Collectors.toMap(Roles::getRoleName, Function.identity()));
    }


    private void assignPermissions(Roles role,
                                   Map<ResourcesName, Resource> resourceMap,
                                   Map<ACTIONS, Actions> actionMap,
                                   List<ResourcesName> resources,
                                   List<ACTIONS> actionsList)
    {

        List<RolePermission> toInsert = new ArrayList<>();

        for (ResourcesName resourceName : resources) {
            for (ACTIONS act : actionsList) {
                Resource resource = resourceMap.get(resourceName);
                Actions action = actionMap.get(act);

                permissionRepository.findPermissionByActionsIdAndResourceId(action.getId(), resource.getId())
                        .ifPresent(permission -> {
                            boolean alreadyExists = rolePermissionRepository
                                    .findByRoleIdAndPermissionId(role.getId(), permission.getId())
                                    .isPresent();

                            if (!alreadyExists) {
                                toInsert.add(RolePermission.builder()
                                        .role(role)
                                                .tenantId(GlobalConstant.GLOBAL_TENANT_ID)
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



