package com.lms.usermanagementservice.Repository.Global;

import com.LMS.Constants.ACTIONS;
import com.lms.usermanagementservice.Model.Globals.Global_Actions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface Global_ActionsRepository extends JpaRepository<Global_Actions, Long> {

    Optional<Global_Actions> findActionsByAction(ACTIONS action);

    List<Global_Actions> findByActionIn(Collection<ACTIONS> actions);
}
