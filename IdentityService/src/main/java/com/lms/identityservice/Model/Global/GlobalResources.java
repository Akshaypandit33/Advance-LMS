package com.lms.identityservice.Model.Global;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Indexed;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        schema = "global_metadata",
        name = "global_resources",
        indexes = {
                @Index(name = "idx_global_resources_resource_type", columnList = "resourceType")
        }
)
public class GlobalResources {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String description;

    private String resourceType;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if(this.name != null && this.resourceType != null) {
            this.setName(this.getName().toUpperCase());
            this.setResourceType(this.getResourceType().toUpperCase());
        }
        if(description == null || description.isEmpty()) {
            this.setDescription("Resource for "+this.getName());
        }
    }
}
