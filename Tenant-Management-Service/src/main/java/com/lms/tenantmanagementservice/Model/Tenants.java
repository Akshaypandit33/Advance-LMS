package com.lms.tenantmanagementservice.Model;

import com.LMS.Constants.TenantStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenants {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String collegeCode;
    private String name;

    private String logoUrl;

    @Column(columnDefinition = "jsonb")
    private String theme;  // Store as JSON: { "primary": "#1a1a1a", "accent": "#007bff", ... }


    @Column(unique = true)
    private String schemaName;

    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private TenantStatus status;

    @Column(name = "admin_user_id", nullable = true)
    private UUID adminUserId;

    @CreationTimestamp
    private ZonedDateTime addedAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        schemaName=schemaName.toLowerCase();
        name=name.toLowerCase();
    }
}
