package com.camunda.demo.delegates;

import com.camunda.demo.Model.LeaveMonitoring;
import com.camunda.demo.Service.EmailServiceImpl;
import com.camunda.demo.repositories.LeaveMonitoringRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:37
 * @project my-project
 */
@Component
public class DeclineLeave implements JavaDelegate {

    LeaveMonitoringRepository leaveMonitoringRepository;
    EmailServiceImpl emailService;
    public DeclineLeave(LeaveMonitoringRepository leaveMonitoringRepository, EmailServiceImpl emailService) {
        this.leaveMonitoringRepository = leaveMonitoringRepository;
        this.emailService = emailService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        //send approval email
        emailService.sendSimpleMessage(delegateExecution.getVariable("employeeEmail").toString(), "Leave Application Notification"
                , "Dear " + delegateExecution.getVariable("employeeName").
                        toString() + "\n\nKindly note that your Leave application has been rejected.\n\n" +
                        "Your leave Balance of  " + delegateExecution.getVariable("closingLeaveBalance").toString() +
                        " Exceeds the maximum limit of -15.\n\nKind regards\n\nHRMS System");
        LeaveMonitoring leaveMonitoring = leaveMonitoringRepository.findById(Long.valueOf(delegateExecution.getProcessBusinessKey())).orElse(null);
        leaveMonitoring.setStatus("REJECTED");

        leaveMonitoring.setApprover("SYSTEM");
        leaveMonitoringRepository.save(leaveMonitoring);
        delegateExecution.setVariable("assignee",leaveMonitoring.getEmployee_number());



    }

}
