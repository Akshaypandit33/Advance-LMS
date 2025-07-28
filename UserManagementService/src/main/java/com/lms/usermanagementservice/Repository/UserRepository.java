package com.lms.usermanagementservice.Repository;

import com.lms.usermanagementservice.Model.Users;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("""
    SELECT u FROM Users u
    WHERE (
        LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(u.lastName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
        u.phoneNumber LIKE CONCAT('%', :keyword, '%')
    )
""")
    Page<Users> searchUsers(@Param("keyword") String keyword, Pageable pageable);



}
