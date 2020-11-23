package com.camunda.demo.delegates;

import com.camunda.demo.Model.LeaveMonitoring;
import com.camunda.demo.Service.EmailServiceImpl;
import com.camunda.demo.Service.LeaveService;
import com.camunda.demo.repositories.LeaveMonitoringRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:38
 * @project my-project
 */
@Component
public class LeaveApprovalDelegate implements JavaDelegate {

    LeaveMonitoringRepository leaveMonitoringRepository;

    EmailServiceImpl emailService;

    LeaveService leaveService;

    public LeaveApprovalDelegate(LeaveMonitoringRepository leaveMonitoringRepository, EmailServiceImpl emailService,LeaveService leaveService) {
        this.leaveMonitoringRepository = leaveMonitoringRepository;
        this.emailService = emailService;
        this.leaveService =leaveService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        if(delegateExecution.getVariable("approved").toString().contentEquals(Boolean.TRUE.toString())){
            //send approval email
            emailService.sendSimpleMessage(delegateExecution.getVariable("employeeEmail").toString(),"Leave Application Notification"
                    , "Dear "+ delegateExecution.getVariable("employeeName").
                            toString()+"\n\nKindly note that your Leave application has been approved.\n\n" +
                            "Your leave Balance is now " + delegateExecution.getVariable("closingLeaveBalance").toString()+
                            "\n\nKind regards\n\nHRMS System");
            LeaveMonitoring leaveMonitoring = leaveMonitoringRepository.findById(Long.valueOf(delegateExecution.getProcessBusinessKey())).orElse(null);
            leaveMonitoring.setStatus("APPROVED");

            leaveMonitoring.setApprover(delegateExecution.getVariable("assignee").toString());
            leaveMonitoringRepository.save(leaveMonitoring);

            leaveService.takeLeave(leaveMonitoring);
        }else{
            //send rejection email
            emailService.sendSimpleMessage(delegateExecution.getVariable("employeeEmail").toString(),"Leave Application Notification"
                    , "Dear "+ delegateExecution.getVariable("employeeName").toString()+"\n\nKindly note that your Leave application has been rejected.\n\nKind regards\n\nHRMS System");

            LeaveMonitoring leaveMonitoring = leaveMonitoringRepository.findById(Long.valueOf(delegateExecution.getProcessBusinessKey())).orElse(null);
            leaveMonitoring.setStatus("REJECTED");
            delegateExecution.setVariable("assignee",leaveMonitoring.getEmployee_number());
            leaveMonitoring.setApprover(delegateExecution.getVariable("assignee").toString());
            leaveMonitoringRepository.save(leaveMonitoring);

        }
    }
}
