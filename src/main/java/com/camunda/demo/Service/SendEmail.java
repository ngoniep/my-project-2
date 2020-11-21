package com.camunda.demo.Service;


import org.springframework.stereotype.Service;


@Service
public class SendEmail {

  /*  @Autowired
    private JavaMailSender javaMailSender;




   public void sendEmail() {


        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("pardon9094@gmail.com", "pardon.white@fbc.co.zw");

        msg.setSubject("Testing from Spring Boot");
        msg.setText("Hello World \n Spring Boot Email");

        javaMailSender.send(msg);

    }

  public   void sendEmailWithAttachment(Application application, List anomalies, PersonDTO personDTO) throws MessagingException, IOException {

        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo("pardon.white@fbc.co.zw");

        helper.setSubject("Application Details for "+application.getPersonalDetails().getFirstName()+" "+application.getPersonalDetails().getLastName());


        // default = text/plain
        //helper.setText("Check attachment for image!");

        // true = text/html
      String x="";
      if(application.getPersonalDetails().getIdNumber().replace(" ","").replace("-","").equals((application.getPersonalDetailsOcr().getIdNumber()==null)?"":application.getPersonalDetailsOcr().getIdNumber().replace(" ","").replace("-","")))
               x+="<div>";
      else x+="<p>";
      x+="<table>\n" +
              "  <tr>\n" +
              "    <th></th>\n" +
              "    <th>Entered by the Client</th>\n" +
              "    <th>Extracted By OCR</th>\n" +
              "    <th>Information from the Registrar General</th>\n" +
              "  </tr>\n" +

              " <tr>\n" +
              "    <td>First Name</td>\n" +
              "    <td>"+application.getPersonalDetails().getFirstName()+"</td>\n" +
              "    <td>"+application.getPersonalDetailsOcr().getFirstName()+"</td>\n" +
              "    <td>"+personDTO.getFirstName()+"</td>\n" +
              "  </tr>"+
              " <tr>\n" +
              "    <td>Last Name</td>\n" +
              "    <td>"+application.getPersonalDetails().getLastName()+"</td>\n" +
              "    <td>"+application.getPersonalDetailsOcr().getLastName()+"</td>\n" +
              "    <td>"+personDTO.getSurname()+"</td>\n" +
              "  </tr>"+
              " <tr>\n" +
              "    <td>Gender</td>\n" +
              "    <td>"+application.getPersonalDetails().getGender()+"</td>\n" +
              "    <td>"+application.getPersonalDetailsOcr().getGender()+"</td>\n" +
              "    <td>"+personDTO.getSex()+"</td>\n" +
              "  </tr>"+
              " <tr>\n" +
              "    <td>ID Number</td>\n" +
              "    <td>"+application.getPersonalDetails().getIdNumber()+"</td>\n" +
              "    <td>"+application.getPersonalDetailsOcr().getIdNumber()+"</td>\n" +
              "    <td>"+personDTO.getPersonNo()+"</td>\n" +
              "  </tr>"+
              " <tr>\n" +
              "    <td>ID Number</td>\n" +
              "    <td>"+application.getPersonalDetails().getDateOfBirth()+"</td>\n" +
              "    <td>"+application.getPersonalDetailsOcr().getDateOfBirth()+"</td>\n" +
              "    <td>"+personDTO.getDateOfBirth()+"</td>\n" +
              "  </tr>"+
              "</table>";
      if(application.getPersonalDetails().getIdNumber()
              .equals(application.getPersonalDetailsOcr().getIdNumber())
      )
          x+="</div>";
      else x+="/p";
        String m="<h1>Here are the results for the "+application.getPersonalDetails().getFirstName()+"'s application</h1>";
      for (Object s: anomalies ) {
          m+=s;
      }

      String image="<img src=\"D:\\faceImage.png\" alt=\"HTML5 Icon\" width=\"450\" height=\"450\">";
      image+="<img src=\"D:\\idDocument.png\" alt=\"HTML5 Icon\" width=\"450\" height=\"450\"><br/>"+x;
      helper.setText(m+image,true);



        helper.addAttachment("face_image.png", new File("D:\\faceImage.png"));
        helper.addAttachment("id.png", new File("D:\\idDocument.png"));

        javaMailSender.send(msg);

    }*/
}

