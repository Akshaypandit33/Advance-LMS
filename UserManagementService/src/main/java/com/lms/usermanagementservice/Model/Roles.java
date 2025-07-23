package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "roles",
        indexes = {
                @Index(name = "idx_roles_name", columnList = "role_name"),
                @Index(name = "idx_roles_system_defined", columnList = "is_system_defined"),
                @Index(name = "idx_roles_name_system", columnList = "role_name, is_system_defined")
        }
)
public class Roles extends BaseClass {

    @Column(name = "role_name", length = 100, nullable = false, unique = true)
    private String roleName;

    @Column(name = "role_description", length = 500)
    private String roleDescription;

    @Column(name = "is_system_defined", nullable = false)
    private boolean isSystemDefined = false;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;

    @Builder.Default
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new HashSet<>();

    @Transient
    public Set<Permission> getPermissions() {
        return rolePermissions.stream()
                .map(RolePermission::getPermission)
                .collect(Collectors.toSet());
    }

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        roleName = roleName.toUpperCase();
    }
}
