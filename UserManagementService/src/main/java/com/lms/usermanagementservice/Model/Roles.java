package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Roles extends BaseClass {

    private String roleName;
    private String roleDescription;

    private boolean isSystemDefined = false;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @Builder.Default
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new HashSet<>();



    @Transient
    public Set<Permission> getPermissions() {
        return rolePermissions.stream().map(RolePermission::getPermission).collect(Collectors.toSet());
    }

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        roleName = roleName.toUpperCase();
    }
}
