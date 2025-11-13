package com.lms.identityservice.Model.Global;


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
@Table(
        schema = "global_metadata",
        name = "global_actions",
        indexes = {
                @Index(name = "idx_global_actions_name", columnList = "name"),
                @Index(name = "idx_global_actions_created_at", columnList = "createdAt")
        }
)
public class GlobalActions {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String name;

    private String description;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if(this.name != null) {
            this.setName(this.getName().toUpperCase());
        }
    }
}
