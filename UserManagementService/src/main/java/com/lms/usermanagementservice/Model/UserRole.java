package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(
        name = "user_roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "role_id"})
        },
        indexes = {
                @Index(name = "idx_user_roles_user_id", columnList = "user_id"),
                @Index(name = "idx_user_roles_role_id", columnList = "role_id")
        }
)

public class UserRole extends BaseClass {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by", foreignKey = @ForeignKey(name = "fk_user_roles_assigned_by"))
    private Users assignedBy;

    @Column(name = "assigned_at")
    private ZonedDateTime assignedAt;

    @PrePersist
    public void onCreate()
    {
        if (this.assignedAt == null) {
            this.assignedAt = ZonedDateTime.now(); // Only if not set explicitly
        }
    }

}

