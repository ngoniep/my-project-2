package com.camunda.demo.leavemanagement;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AvailableDaysDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable("availableDays", 10);
        try {
            String date = delegateExecution.getVariable("startDate").toString();
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            System.out.println("the date is " + formatter.parse(date));
            SimpleDateFormat requiredFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = formatter.parse(date);
            String startDateString = requiredFormat.format(startDate);
            delegateExecution.setVariable("startDateString", startDateString);


            date = delegateExecution.getVariable("endDate").toString();
            Date endDate = formatter.parse(date);
            String endDateString = requiredFormat.format(endDate);
            delegateExecution.setVariable("endDateString", endDateString);
            long diff = endDate.getTime() - startDate.getTime();
            long numDaysApplied = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            delegateExecution.setVariable("numDaysApplied", numDaysApplied);
            System.out.println("Here is the date in the required format " + requiredFormat.parse(requiredFormat.format(endDate)));

        } catch (Exception e) {
            try {
                SimpleDateFormat requiredFormat = new SimpleDateFormat("dd/MM/yyyy");
                String date = delegateExecution.getVariable("stDate").toString();
                delegateExecution.setVariable("startDateString", date);
                String endDate = delegateExecution.getVariable("eDate").toString();
                Date endDate1 = requiredFormat.parse(endDate);
                Date startDate = requiredFormat.parse(date);
                long diff = endDate1.getTime() - startDate.getTime();
                long numDaysApplied = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                delegateExecution.setVariable("numDaysApplied", numDaysApplied);
                delegateExecution.setVariable("endDateString", endDate);

            }catch (Exception ignored){

            }


        }
    }
}
