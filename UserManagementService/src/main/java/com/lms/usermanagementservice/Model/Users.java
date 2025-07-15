package com.lms.usermanagementservice.Model;

import com.LMS.BaseClass;
import com.LMS.Constants.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Users extends BaseClass {

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private String gender;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRole> userRoles = new HashSet<>();

    public boolean hasRole(String roleName) {
        return userRoles.stream().anyMatch(r -> r.getRole().getRoleName().equalsIgnoreCase(roleName));
    }

    public boolean isSuperAdmin() {
        return hasRole("SUPER_ADMIN");
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
