package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "permissions", uniqueConstraints = @UniqueConstraint(columnNames = {"action_id", "resource_id", "tenant_id"}))

public class Permission extends BaseClass{

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Actions actions;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource; // e.g. "COURSE"

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolePermissions;

    public String getName() {
        return resource.getResourceName() + "_" + actions.getAction();
    }
}
