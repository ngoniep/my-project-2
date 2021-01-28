package com.camunda.demo;

import com.camunda.demo.BusinessModels.SanctionScore;
import com.camunda.demo.Service.InternalSanctionSearchFeign;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InternalSanctionsDelegate implements JavaDelegate {

    @Autowired
    InternalSanctionSearchFeign.InternalSanctionsClient internalSanctionsClient;

//    @Value("${configurations.sanctionsScreening: http://10.170.3.40:9781/kyc-screening-service/screen}")
//    String sanctionsUrl;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {


        delegateExecution.setVariable("sanctionScore", 1.0);
       // InternalSanctionsService internalSanctionsService= new InternalSanctionsService();
        String firstName=delegateExecution.getVariable("firstName").toString();
        String lastName=delegateExecution.getVariable("lastName").toString();
        String dateOfBirth=delegateExecution.getVariable("registrarDateOfBirth").toString();
        String[] firstNames=firstName.split(" ");
        String middleName="XQZD";
        if(firstNames.length>1){
            firstName=firstNames[0];
            middleName=firstNames[1];
        }
        try {
            /*OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(sanctionsUrl+"?firstname="+firstName+"&middlename="+middleName+"&surname="+lastName+"&datOfBirth="+dateOfBirth)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            SanctionScore sanctionScore = gson.fromJson(response.body().string(), SanctionScore.class);*/

            SanctionScore sanctionScore=internalSanctionsClient.getPersonalDetailsV1(firstName,middleName,lastName,dateOfBirth);
            //System.out.println("result of sanctions is " + sanctionScore);
            delegateExecution.setVariable("sanctionScore", (sanctionScore.getMatchScore()==null)?0.0:sanctionScore.getMatchScore());
        }catch(Exception e){
            e.printStackTrace();

        }


    }
}
