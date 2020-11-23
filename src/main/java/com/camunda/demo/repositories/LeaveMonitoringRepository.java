package com.camunda.demo.repositories;

import com.camunda.demo.Model.LeaveMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:44
 * @project my-project
 */
@Repository
public interface LeaveMonitoringRepository extends JpaRepository<LeaveMonitoring,Long> {
}

