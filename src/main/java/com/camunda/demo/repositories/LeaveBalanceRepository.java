package com.camunda.demo.repositories;

import com.camunda.demo.Model.LeaveBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:44
 * @project my-project
 */
@Repository
public interface LeaveBalanceRepository extends JpaRepository<LeaveBalance, Long> {

    @Query("select l from LeaveBalance l where l.employee_number=?1")
    Optional<LeaveBalance> findLeaveBalanceByEmployee_number(String employee_number);
}
