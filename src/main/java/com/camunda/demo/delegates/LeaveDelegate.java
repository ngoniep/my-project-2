package com.camunda.demo.delegates;

import com.camunda.demo.Model.LeaveBalance;
import com.camunda.demo.Model.LeaveMonitoring;
import com.camunda.demo.Service.LeaveService;
import com.camunda.demo.enums.Holidays;
import com.camunda.demo.repositories.LeaveMonitoringRepository;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:39
 * @project my-project
 */
@Component
public class LeaveDelegate implements JavaDelegate {
    final
    LeaveMonitoringRepository leaveMonitoringRepository;

    LeaveService leaveService;



    public LeaveDelegate(LeaveMonitoringRepository leaveMonitoringRepository, LeaveService leaveService) {
        this.leaveMonitoringRepository = leaveMonitoringRepository;
        this.leaveService =leaveService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
       /* Date startDate = (Date) delegateExecution.getVariable("startDate");
        Date endDate = (Date) delegateExecution.getVariable("endDate");
        Date d1 = new SimpleDateFormat("yyyy-M-dd").parse(startDate.toString());
        Date d2 = new SimpleDateFormat("yyyy-M-dd").parse(endDate.toString());
        long diff = d2.getTime() - d1.getTime();*/
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        LeaveMonitoring leaveMonitoring = new LeaveMonitoring();
        leaveMonitoring.setEmployee_number((String) delegateExecution.getVariable("employeeNumber"));
        leaveMonitoring.setFullname((String) delegateExecution.getVariable("employeeName"));
        leaveMonitoring.setLeave_type((String) delegateExecution.getVariable("leaveType"));
        leaveMonitoring.setComments((String) delegateExecution.getVariable("comment"));
        leaveMonitoring.setDept("Department");
        leaveMonitoring.setStatus("CREATED");
        leaveMonitoring.setPeriod_from(formatter.parse((String) delegateExecution.getVariable("startDate")));
        leaveMonitoring.setPeriod_to(formatter.parse((String) delegateExecution.getVariable("endDate")));

        double closing_leave_balance = 0;
        long diffInMillies = Math.abs(leaveMonitoring.getPeriod_to().getTime() - leaveMonitoring.getPeriod_from().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        Optional<LeaveBalance> leaveBalance = leaveService.getLeaveBalance(leaveMonitoring.getEmployee_number());
        double leaveBalanceNumber = 0;

        if(leaveMonitoring.getLeave_type().contentEquals("Annual")) {
            if(leaveBalance.isPresent()){
                leaveBalanceNumber = leaveBalance.get().getOpening_leave_balance() - leaveBalance.get().getDays_taken();
            }
            closing_leave_balance =leaveBalanceNumber - leaveMonitoring.getNumber_of_days();
            leaveMonitoring.setNumber_of_days(Holidays.ZIMBABWE.getBusinessDayCount(leaveMonitoring.getPeriod_from(), leaveMonitoring.getPeriod_to()) + 1);
        }else{
            leaveBalanceNumber = 0;
            leaveMonitoring.setNumber_of_days(Math.toIntExact(diff ) + 1);
        }


        leaveMonitoringRepository.save(leaveMonitoring);

        delegateExecution.setVariable("numDaysApplied",leaveMonitoring.getNumber_of_days());
        delegateExecution.setVariable("openingLeaveBalance", leaveBalanceNumber);
        delegateExecution.setVariable("closingLeaveBalance", closing_leave_balance);
        delegateExecution.setProcessBusinessKey(leaveMonitoring.getId().toString());
    }
}
