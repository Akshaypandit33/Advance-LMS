package com.lms.identityservice.Model.Tenant;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    private String roleName;
    private String roleDescription;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        this.roleName = this.roleName.toUpperCase();
    }
}
