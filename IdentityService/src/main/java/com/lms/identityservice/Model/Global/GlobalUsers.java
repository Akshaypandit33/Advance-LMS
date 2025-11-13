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
        name = "global_users",
        indexes = {
                @Index(name = "idx_global_users_email", columnList = "email", unique = true),
                @Index(name = "idx_global_users_phone", columnList = "phoneNumber"),
                @Index(name = "idx_global_users_name", columnList = "firstName,lastName")
        }
)
public class GlobalUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    private String email;
    private String password_hash;

    private String phoneNumber;
    private String gender;

    private boolean isSuperAdmin;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}
