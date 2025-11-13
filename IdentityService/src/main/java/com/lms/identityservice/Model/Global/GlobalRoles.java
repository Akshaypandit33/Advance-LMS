package com.lms.identityservice.Model.Global;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
        name = "global_roles",
        indexes = {
                @Index(name = "idx_global_roles_name", columnList = "name", unique = true)

        }
)
public class GlobalRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String Description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "global_role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<GlobalPermissions> permissions= new HashSet<>();

    private boolean isTemplate = false;
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void prePersist() {
        if (this.name != null) {
            this.setName(this.getName().toUpperCase());
        }
    }

}
