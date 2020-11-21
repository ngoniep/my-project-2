package com.camunda.demo;

import com.camunda.demo.Model.DTOs.*;
import com.camunda.demo.Model.OCRPerson;
import com.camunda.demo.Model.OCRRequest;
import com.camunda.demo.Model.ProductApplication;
import com.camunda.demo.Service.*;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class FaceCompareDelegate implements JavaDelegate {

    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    KairosServices enrollmentClient;


    @Autowired
    PersonFeignService.PersonClient personClient;
    @Autowired
    FileCreationService fileCreationService;

    @Autowired
    SMSAlertService smsAlertService;

    @Autowired
    GatewayServiceFeign.ProductApplicationClient ocrService;


        @Override
        public void execute(DelegateExecution delegateExecution) throws Exception {

            System.out.println("Entered Face Compare Delegate");
            delegateExecution.setVariable("accountNumber", "");
            delegateExecution.setVariable("emailAddress", "");
            delegateExecution.setVariable("idImage","");
            delegateExecution.setVariable("instantCardCreationMessage", "");
            delegateExecution.setVariable("instantCardCreationResponseCode", "");
            delegateExecution.setVariable("instantCardCreationRetrievalRef", "");
            delegateExecution.setVariable("businessLicenseNumber","");
            delegateExecution.setVariable("similarityScore",0);
            delegateExecution.setVariable("isUSSD",false);
            delegateExecution.setVariable("facialImageURl","http://fbclptisoff001/WebServicE/faceImage.png");
            delegateExecution.setVariable("drivingLicenseIssuerAuthority","");
            delegateExecution.setVariable("accountEligible",false);
            delegateExecution.setVariable("faceImageLink","D:\\Profile\\Dowloads\\process.png");
            delegateExecution.setVariable("latestRiskScore","0");
            delegateExecution.setVariable("address","");

            System.out.println("Person Number is -> "+delegateExecution.getVariable("personNo").toString());

            PersonalDetailsDTO personalDetails=personClient.getPersonalDetailsByIdNumber(delegateExecution.getVariable("personNo").toString()).get();

//            smsAlertService.sendSMS(personalDetails.getPrimaryPhoneNumber(), "We are verifying your account information, this usually takes between 5 minutes and one hour, but can sometimes take up to 48 hours. Please check back later  "
//            );
            try {
                fileCreationService.createFile(FileSaveRequest.builder()
                    .imageType("ID_DOCUMENT")
                    .imageBase64String(personalDetails.getIdImageBase64String())
                    .idNumber(personalDetails.getIdNumber())
                    .extension("jpg")
                    .build());
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                fileCreationService.createFile(FileSaveRequest.builder()
                    .imageType("SELFIE")
                    .imageBase64String(personalDetails.getSelfieBase64String())
                    .idNumber(personalDetails.getIdNumber())
                    .extension("jpg")
                    .build());
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                fileCreationService.createFile(FileSaveRequest.builder()
                    .imageType("PROOF_OF_RES")
                    .imageBase64String(personalDetails.getProofOfResidenceBase64String())
                    .idNumber(personalDetails.getIdNumber())
                    .extension("jpg")
                    .build());
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                fileCreationService.createFile(FileSaveRequest.builder()
                    .imageType("SIGNATURE")
                    .imageBase64String(personalDetails.getSignatureBase64String())
                    .idNumber(personalDetails.getIdNumber())
                    .extension("jpg")
                    .build());
            }catch (Exception e){
                e.printStackTrace();
            }
            try{
                for(ContactDetails contactDetails:personalDetails.getContactDetails()){
                    if(contactDetails.getContactType().toLowerCase().contains("email")){
                        delegateExecution.setVariable("emailAddress",contactDetails.getContactValue());
                    }
                }

            }catch (Exception ignored){

            }


            delegateExecution.setVariable("idImageUrl","http://10.170.3.46/WebService/ID_DOCUMENT-"+personalDetails.getIdNumber()+".jpg");
            delegateExecution.setVariable("selfieImageUrl","http://10.170.3.46/WebService/SELFIE-"+personalDetails.getIdNumber()+".jpg");
            delegateExecution.setVariable("proofOfResImageUrl","http://10.170.3.46/WebService/PROOF_OF_RES-"+personalDetails.getIdNumber()+".jpg");
            delegateExecution.setVariable("signatureImageUrl","http://10.170.3.46/WebService/SIGNATURE-"+personalDetails.getIdNumber()+".jpg");
            delegateExecution.setVariable("firstName",personalDetails.getFirstName());
            delegateExecution.setVariable("lastName",personalDetails.getLastName());

            KairosEnrolResponse kairosEnrolResponse= enrollmentClient.enrollUser(KairosEnrollObject.builder()
                .gallery_name("MyGallery")
                .image(personalDetails.getSelfieBase64String())
                .selector("liveness")
                .subject_id(delegateExecution.getVariable("personNo").toString())
                .build());
            //String idImage = delegateExecution.getVariable("idImage").toString();
            System.out.println("-------Starting facial recognition-----------");
            KairosRecognizeResponse kairosRecognizeResponse= enrollmentClient.recognize(
                KairosRecognizeObject.builder()
                    .gallery_name("MyGallery")
                    .image(personalDetails.getIdImageBase64String())
                    .build()
            );




            System.out.println(kairosRecognizeResponse);
            System.out.println("-------Ending facial recognition-----------");
            try {
                System.out.println(kairosEnrolResponse);

                delegateExecution.setVariable("selfieLivenessScore", kairosEnrolResponse.getImages().get(0).getAttributes().getLiveness());
            }catch (NullPointerException e){

            } catch (Exception e){
                e.printStackTrace();
            }
            double match=0;


            try {
                if(!(kairosRecognizeResponse==null))
                for (RecognizeImages recognizeImages : kairosRecognizeResponse.getImages()) {
                    System.out.println("Checking the candidate");

                    if (recognizeImages.getCandidates() != null) {
                        System.out.println(recognizeImages.getCandidates().size());
                        for (Candidates candidates : recognizeImages.getCandidates()) {
                            System.out.println("Candidate " + candidates.getFace_id());
                            if (candidates.getSubject_id().equals(delegateExecution.getVariable("personNo").toString())) {
                                match = candidates.getConfidence();
                                System.out.println(match);
                                break;
                            }
                        }
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            boolean imagesSimilar = (match > 0.6);

            try{
                OCRPerson ocrPerson=ocrService.doOCR(
                    OCRRequest.builder()
                        .idImageBase64String(personalDetails.getIdImageBase64String())
                        .build()
                );
               if(!ocrPerson.getIdNumber().equalsIgnoreCase(delegateExecution.getVariable("personNo").toString())){
                   imagesSimilar=false;
               }
            }catch (Exception e){
                imagesSimilar=false;
              //  e.printStackTrace();
            }

            try{
                String address = "";
                if (personalDetails.getAddresses() != null) {
                    for (Address add : personalDetails.getAddresses()) {
                        address = add.getStreetName() + " " + add.getCity() + " " + add.getCountry();
                    }
                }

                delegateExecution.setVariable("address",address);

            }catch (Exception ignored){

            }

            delegateExecution.setVariable("imagesSimilar", imagesSimilar);
            System.out.println("Match confidence is "+match);
            delegateExecution.setVariable("similarityScore", match);
            System.out.print("The Business Key is "+delegateExecution.getProcessBusinessKey());
            ProductApplication productApplication= productApplicationClient.trackApplication(delegateExecution.getBusinessKey());
            // productApplication.setApplicationStatus("Application 7% Complete");
            productApplication.setApplicationStatus(CamundaApplication.APPLICATION_STATUS.IN_PROGRESS);
            productApplication.setApplicationStatusDescription("Application in Progress");
            if(productApplication.getPercentageCompletion()<7.00)
                productApplication.setPercentageCompletion(7.00);
            productApplicationClient.updateProductApplication(productApplication);
            productApplicationClient.updateProductApplication(productApplication);

        }
    }
