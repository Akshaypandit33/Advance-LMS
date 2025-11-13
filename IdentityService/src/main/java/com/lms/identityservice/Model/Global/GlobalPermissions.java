package com.lms.identityservice.Model.Global;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        schema = "global_metadata",
        name = "global_permissions",
        indexes = {
                @Index(name = "idx_global_permissions_action_id", columnList = "action_id"),
                @Index(name = "idx_global_permissions_resource_id", columnList = "resource_id")
        }
)
public class GlobalPermissions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = false)
    private GlobalActions action;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private GlobalResources resources;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<GlobalRoles> globalRoles = new HashSet<>();
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if(this.action.getName() != null && this.resources.getName() != null) {

            this.setName(this.action.getName() +"_"+ this.resources.getName());
        }
        if(this.description == null)
        {
            this.setDescription("Allows "+this.getAction().getName()+" on "+this.getResources().getName());
        }
    }
}
