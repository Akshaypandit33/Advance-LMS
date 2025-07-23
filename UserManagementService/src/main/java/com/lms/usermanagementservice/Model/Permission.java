package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(
        name = "permissions",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_permissions_action_resource", columnNames = {"action_id", "resource_id"})
        },
        indexes = {
                @Index(name = "idx_permissions_action_id", columnList = "action_id"),
                @Index(name = "idx_permissions_resource_id", columnList = "resource_id"),
                @Index(name = "idx_permissions_composite", columnList = "resource_id, action_id")
        }
)
public class Permission extends BaseClass {

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false, foreignKey = @ForeignKey(name = "fk_permissions_action"))
    private Actions actions;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false, foreignKey = @ForeignKey(name = "fk_permissions_resource"))
    private Resource resource;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

    public String getName() {
        return resource.getResourceName() + "_" + actions.getAction();
    }
}
