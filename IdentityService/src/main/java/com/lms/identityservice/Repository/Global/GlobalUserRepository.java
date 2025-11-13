package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GlobalUserRepository extends JpaRepository<GlobalUsers, UUID> {
}
