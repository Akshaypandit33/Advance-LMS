package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import com.LMS.Constants.AccountStatus;
import com.LMS.Constants.DefaultRoles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_email", columnList = "email"),
                @Index(name = "idx_users_account_status", columnList = "account_status"),
                @Index(name = "idx_users_created_at", columnList = "created_at")
        }
)
public class Users extends BaseClass {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "gender", length = 20)
    private String gender;

    @Column(name = "account_status", length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    public boolean hasRole(String roleName) {
        return userRoles.stream().anyMatch(r -> r.getRole().getRoleName().equalsIgnoreCase(roleName));
    }

    public boolean isSuperAdmin() {
        return hasRole(DefaultRoles.SUPER_ADMIN.name());
    }
    // Optional: lazy-loaded derived roles
    @Transient
    public Set<Roles> getRoles() {
        return userRoles.stream().map(UserRole::getRole).collect(Collectors.toSet());
    }

    @PrePersist
    @PreUpdate
    public void prePersist() {
        email = email.toLowerCase();
    }
}
