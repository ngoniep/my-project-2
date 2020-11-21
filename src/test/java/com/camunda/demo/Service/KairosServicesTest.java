package com.camunda.demo.Service;


import com.camunda.demo.Model.DTOs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class KairosServicesTest {

  /*  @Autowired
    KairosServices enrollmentClient; ;

    @Autowired
    GatewayServiceFeign.ProductApplicationClient productApplicationClient;

    @Autowired
    PersonFeignService.PersonClient personDetailsClient;

    @Autowired
    FileCreationService fileCreationService;

    @Autowired
    VerifyClientExistense verifyClientExistense;


    @Autowired
    EnableSMSAlerts enableSMSAlerts;

    @Autowired
    GatewayServiceFeign.ProductApplicationClient ocrService;

    @Test
    public void test(){
        KairosEnrollObject kairosEnrollObject=KairosEnrollObject.builder()
            .gallery_name("MyGallery")
            .image("https://media.kairos.com/kairos-elizabeth.jpg")
            .selector("liveness")
            .subject_id("Elizabeth")
            .build();
        KairosEnrolResponse kairosEnrolResponse=enrollmentClient.enrollUser(kairosEnrollObject);
        KairosRecognizeObject kairosRecognizeObject=KairosRecognizeObject.builder()
            .gallery_name("MyGallery")
            .image("https://media.kairos.com/kairos-elizabeth.jpg")
            .build();
        System.out.print(kairosEnrolResponse);
        KairosRecognizeResponse kairosRecognizeResponse=enrollmentClient.recognize(kairosRecognizeObject);
        System.out.print(kairosRecognizeResponse);
    }*/

/*    @Test
    public void testImages(){
        fileCreationService.createFile(FileSaveRequest.builder()
            .extension("jpg")
            .idNumber("420246347R71")
            .imageBase64String(personDetailsClient.getPersonalDetails("420246347R71").getSelfieBase64String())
            .imageType("ID")
            .build());
    }

    @Test
    public void testRec(){
        PersonalDetailsDTO personalDetails=personDetailsClient.getPersonalDetails("42246347R71");

        System.out.println(personalDetails.getIdImageBase64String());
        System.out.println("It is "+personalDetails.getIdImageBase64String().equals(personalDetails.getSelfieBase64String())+" " +
            "That selfie and ID are the same object");
        KairosRecognizeObject kairosEnrollObject= KairosRecognizeObject.builder()
            .gallery_name("MyGallery")
            .image(personalDetails.getIdImageBase64String())
            .build();
        KairosRecognizeResponse kairosEnrolResponse=enrollmentClient.recognize(kairosEnrollObject);
        System.out.print(kairosEnrolResponse);
    }


    @Test
        public void getPerson() throws IOException {
        ProductApplication productApplication=productApplicationClient.trackApplication("5f2bf326dacced7847b1a675");
        System.out.println("application details -> "+productApplication);
        productApplication.getAccount().setAccountNumber("6017042200001022");

        System.out.println("After updating->"+productApplicationClient.updateProductApplication(productApplication));

    }

    @Test
    public void setVerifyClientExistense() throws Exception {
        Optional<String> accountNumber = verifyClientExistense.verifyClientExists("030144931C03","263779302984","KycLiteAccount");
        Assertions.assertThat(accountNumber).isNotEmpty();
        Assertions.assertThat(accountNumber.get()).isEqualToIgnoringCase("6017041003816016");

    }

    @Test
    public void setVerifyClientExistenseFlex() throws Exception {
        Optional<String> accountNumber = verifyClientExistense.flexcube("632107256D18","263779405100","Savings","ZWL");
        Assertions.assertThat(accountNumber).isNotEmpty();
  //      Assertions.assertThat(accountNumber.get()).isEqualToIgnoringCase("6017041003816016");

    }


    @Test
    public void checkCustomerNumber() throws Exception {
        Optional<String> accountNumber = verifyClientExistense.existingCustomerNumber("4202463407R71","263779405100","Savings","ZWL");
        Assertions.assertThat(accountNumber).isNotEmpty();
        Assertions.assertThat(accountNumber.get()).isEqualToIgnoringCase("7014969");

    }

    @Test
    public void createCustomer() throws Exception {
        customerCreationServiceFeign.createCustomer(
            CreateCustomerRequest.builder().build()
        );

    }

    @Test
    public void enableSMSAlerts() throws Exception {
        List<String> codes = Arrays.asList("550","RLA","MDS","AOS","VPO","VCO","NOT","POD","VLS",
            "IBI","575","753","RSR","439","BHT","DSL","CMJ","DUO","EVH","579","583","PPX","MTE"
            ,"TBY","2PE","PLL","IBB","IBR","RZT","630","RSP","SRS","LEC","MMZ","DCO","ASI","BUH","GEL","591","LES","PBZ","SAN","TAZ","IMO"
            ,"GRE","IBG","ADL","BUO","AZB","BRE","SMR","TAF","LLG","SHH","GWZ","VAE","FMM","CLA","ARB","RDO","CMD","NCR","MZR","NBS","AFE",
            "TOM","IRR","TLI","CLM","BHD","TZD","ROD","SNT","ABL","PCO","LLA","SLG","CMG","GTS","OLO","ALG","BRU","RUA","CWZ","GSD","594",
            "PCK","DSI","PTF","DRR","LIW","TDC","TBO","GLD","RAT","EDC","OAT","IPG","MCZ","NTI","LSJ","BTD","653","654","OZT","RWA","BHC",
            "ARE","CN2","IFC","MFS","CIL","ETS","MRA","FXD","ABT","CIT","CB1","C11","BCL","SCN","ZBB","PSN","MRE","MYF","MPI","CIR","HDB",
            "YFS","GED","NBB","ECN","MCA","DTF","BAB","MEP","NED","RBN","AGN","CAN","SNN","CBN","NNB","IDN","NBN","AFR","GTN","STE","MFX",
            "MLM","SUI","ECP","INV","BTW","TED","TEP","CW1","BLW","ZSE","YET","MAD","CL3","APR","ASR","IRO","TSE","TAS","CTC","TIN","TMA",
            "DOS","ODR","ATB","FSP","BBY","IRN","ZFD","365","OWA","CFP","CL5","IMW","MCD","TTO","DN1","NT1","MT1","457","CTW","RTC","RGN",
            "PAZ","PAD","MOD","NRG","FST","MCS","469","PRV","IPB","ITN","BDW","479","DUR","DSV","179","180","182","187","192","191","189",
            "345","346","347","348","349","350","351","352","353","354","355","356","357","358","361","362","370","371","372","373","374",
            "375","534","SOT","406","409","376","378","TAT","FLS","517","526","528","529","536","538","501","502","503","508","510","511",
            "513","393","412","415","418","PSB","FRI","DSE","LTT","HCM","HMT","PFT","BUA","605","381","520","527","531","320","319","322",
            "323","324","325","148","149","150","151","152","153","154","156","157","160","161","163","165","181","168","169","170","620",
            "377","DVF","YAH","TOT","MCH","CAO","IML","GKL","ROH","FFU","WOA","QUE","SPT","JOI","363","AST","PZT","MFM","TFD","BMC","GLF",
            "IBZ","629","490","FZD","SLA","DPC","INE","SSO","MTV","ACJ","PZZ","590","SAI","STN","MLE","LAE","CUE","RCE","GLA","RFA","NSA",
            "WFS","SPS","MFC","NFD","DLA","FCG","GTG","SDD","ARM","FUH","FUS","584","589","MTI","PEF","RLG","ASU","TBS","MMP","WR3","VSA",
            "OMB","GTB","MPS","RSW","MLP","MPP","POL","KUW","MPY","PMI","MTG","KUC","SRW","MYP","IRL","544","PI2","PIW","TW1","LJW","NTW",
            "CWE","DNW","NC1","GKW","GKD","TEW","3PE","NSC","BLC","BR2","MRW","CIW","LPR","PPO","ABX","CI2","TIW","401","419","EPR","ZPP",
            "HCP","ANW","PEO","QAD","DCD","MUW","MUC","596","597","IBT","CRN","JUW","AQD","NAW","EWL","ETU","TAU","ST1","ST2","JTC","SMW",
            "QAW","FAC","CRR","WBR","ARR","543","542","MRD","SMD","AND","PT1","PTE","DIW","UD1","UDE","FAW","BIR","BWR","ETW","514","516",
            "IB9","NEU","545","JTW","MRG","MT2","JIC","AQW","NAC","NCW","FUC","CTD","BAW","WAB","MOI","RUP","ZFW","WIW","WID","364","EAW",
            "CR1","CM3","AFN","IMD","652","SR1","NAD","MTA","BI2","ZTL","580","MFI","URS","MIR","ITZ","IWA","ASH","ANP","ATJ","FYH","LGA",
            "BHO","RAI","MDF","GSF","VAA","NIR","EBZ","ER5","VIP","EXI","RST","BDR","SHD","ALA","MHD"
        );

        enableSMSAlerts.enableSMSAlerts("0400370020001","263719320947",codes);

    }


    @Test
    public void ocrTest(){
       OCRPerson ocrPerson= ocrService.doOCR(OCRRequest.builder()
            .idImageBase64String(personDetailsClient.getPersonalDetails("263777852040").getIdImageBase64String())
            .build());
       Assertions.assertThat(ocrPerson.getLastName()).isEqualToIgnoringCase(personDetailsClient.getPersonalDetails("263777852040").getLastName());
    }*/

}
