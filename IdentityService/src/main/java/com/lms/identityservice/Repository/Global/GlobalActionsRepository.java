package com.lms.identityservice.Repository.Global;

import com.lms.identityservice.Model.Global.GlobalActions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GlobalActionsRepository extends JpaRepository<GlobalActions, UUID> {
    Optional<GlobalActions> findByNameIgnoreCase(String name);
}
