package com.lms.usermanagementservice.Repository;

import com.LMS.Constants.ACTIONS;
import com.lms.usermanagementservice.Model.Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionsRepository extends JpaRepository<Actions, Long> {

    Optional<Actions> findActionsByAction(ACTIONS action);
}
