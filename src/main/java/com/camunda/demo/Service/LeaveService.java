package com.camunda.demo.Service;

import com.camunda.demo.Model.LeaveBalance;
import com.camunda.demo.Model.LeaveMonitoring;
import com.camunda.demo.repositories.LeaveBalanceRepository;
import com.camunda.demo.repositories.LeaveMonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Whatmore
 * @created 23/11/2020 - 14:46
 * @project my-project
 */
@Service
public class LeaveService {
    @Autowired
    LeaveBalanceRepository leaveBalanceRepository;
    @Autowired
    LeaveMonitoringRepository leaveMonitoringRepository;

    public Boolean takeLeave(LeaveMonitoring leaveMonitoring){

        Optional<LeaveBalance> leaveBalance = leaveBalanceRepository.findLeaveBalanceByEmployee_number(leaveMonitoring.getEmployee_number());
        if(leaveBalance.isPresent()){
            leaveBalanceRepository.save(LeaveBalance.builder()
                    .days_taken(leaveBalance.get().getDays_taken()+leaveMonitoring.getNumber_of_days())
                    .build());
        }else{
            leaveBalanceRepository.save(LeaveBalance.builder()
                    .opening_leave_balance(0)
                    .closing_leave_balance(0)
                    .employee_number(leaveMonitoring.getEmployee_number())
                    .days_taken(leaveMonitoring.getNumber_of_days())
                    .build());
        }
        return true;
    }
    public Boolean endOfMonth(){
        List<LeaveBalance> leaveBalances = leaveBalanceRepository.findAll();
        for(LeaveBalance leaveBalance: leaveBalances){

            leaveBalance.setClosing_leave_balance(leaveBalance.getClosing_leave_balance() - leaveBalance.getDays_taken());
            leaveBalance.setOpening_leave_balance(leaveBalance.getClosing_leave_balance() + 2.5);
            leaveBalance.setDays_taken(0);
            leaveBalanceRepository.save(leaveBalance);
        }
        return true;
    }
    public Optional<LeaveBalance> getLeaveBalance(String empNumber){

        return leaveBalanceRepository.findLeaveBalanceByEmployee_number(empNumber);
    }
}

