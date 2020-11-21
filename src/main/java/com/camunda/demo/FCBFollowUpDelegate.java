package com.camunda.demo;

import com.camunda.demo.BusinessModels.FCBReport;
import com.camunda.demo.BusinessModels.FCBRequest;
import com.camunda.demo.BusinessModels.FCBResponse;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.FCBService;
import com.google.gson.Gson;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FCBFollowUpDelegate implements JavaDelegate {


    @Value("${configurations.fcbUrl: https://www.fcbureau.co.zw/api/newIndividual}")
    String fcbUrl;

    @Value("${configurations.sanctionsScreening: http://10.170.3.40:9781/kyc-screening-service/screen}")
    String sanctionsUrl;

    @Override
    public void execute(DelegateExecution delegateExecution)  {

        delegateExecution.setVariable("resultsPending",false);
        delegateExecution.setVariable("fcbId","");

        FCBService fcbService=new FCBService();
        fcbService.setFcbUrl(fcbUrl);
        fcbService.setSanctionsUrl(sanctionsUrl);
        delegateExecution.setVariable("fcbScore",0);
        delegateExecution.setVariable("fcbStatus",0);
        delegateExecution.setVariable("fcbScoreGood",false);
        delegateExecution.setVariable("fcbResponse","");
        try {

            FCBResponse fcbResponse = fcbService.getPerson(delegateExecution.getVariable("fcbId").toString());
            System.out.println(fcbResponse);
            Gson gson = new Gson();
            FCBReport fcbReport = gson.fromJson(fcbResponse.getIndividual(), FCBReport.class);
            try {
                delegateExecution.setVariable("fcbScore", fcbReport.getReport().get(0).getScore());
                delegateExecution.setVariable("fcbStatus", fcbReport.getReport().get(0).getStatus());
                delegateExecution.setVariable("fcbScoreGood", true);
            }catch (Exception e){
                delegateExecution.setVariable("fcbResponse",fcbResponse);
                if(fcbResponse.getIndividual().contains("OPEN"))
                {
                    delegateExecution.setVariable("resultsPending",true);
                }
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }
}
