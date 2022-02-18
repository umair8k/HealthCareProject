package com.hc.service;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public boolean sendEmail(String subject, String message, String to)
    {
        boolean flag = false; 
        String senderEmail = "umair8k@gmail.com"; 
        String senderPassword = "newgmail"; // your gmail id password

        
        Properties properties = new Properties();

       

        properties.put("mail.smtp.auth", "true"); 
        properties.put("mail.smtp.starttls.enable", "true"); 
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587"); 

        // get the session object and pass username and password
        Session session = Session.getDefaultInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); 

            msg.setFrom(new InternetAddress(senderEmail)); 

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 

            msg.setSubject(subject); 
            msg.setText(message); 

            Transport.send(msg); 
            System.out.println("Email Sent Wtih Attachment Successfully...");

            flag = true; 

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return flag;
    }
    //---------------------------------------------------------------------------------------------------------------------

    public boolean sendEmailInlineImage(String subject, String message, String to)
    {
        boolean flag = false; 

        String senderEmail = "umair8k@gmail.com"; 
        String senderPassword = "newgmail"; // your gmail id password

        
        Properties properties = new Properties();


        
        properties.put("mail.smtp.auth", "true"); 
        properties.put("mail.smtp.starttls.enable", "true"); 
        properties.put("mail.smtp.host", "smtp.gmail.com"); 
        properties.put("mail.smtp.port", "587"); 

  
        Session session = Session.getDefaultInstance(properties, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication(){

                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {

            MimeMessage msg = new MimeMessage(session); 

            MimeMessageHelper helper = new MimeMessageHelper(msg, true); 

            helper.setFrom(new InternetAddress(senderEmail)); 

            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to)); 

            helper.setSubject(subject); // adding subject to helper object
           // C:\Users\Mohd Umer Faisal\Downloads\lancesoft_logo.png
            // sets file path location
            String path = "C:\\Users\\Mohd Umer Faisal\\Downloads\\1605809113610.jfif";

            MimeMultipart mimeMultipart = new MimeMultipart(); 

            MimeBodyPart textMime = new MimeBodyPart(); 

            MimeBodyPart messageBodyPart = new MimeBodyPart(); 

            MimeBodyPart fileMime = new MimeBodyPart(); 

            textMime.setText(message); 

            // create message within html format tag and assign to the content variable
            String content = "<br><b>Hi Folks</b>,<br><i>look at this nice logo :)</i>"
            + "<br><img alt='' src='static/images/lancesoft_logo.png'/><br><b>Your Regards Mohd Umer</b>";

           
            messageBodyPart.setContent(content,"text/html; charset=utf-8");
            
            File file = new File(path); 
            fileMime.attachFile(file); 
            
            helper.addInline("lancesoft_logo.png", file); 
         
            mimeMultipart.addBodyPart(textMime);
            mimeMultipart.addBodyPart(messageBodyPart);
            mimeMultipart.addBodyPart(fileMime);

            msg.setContent(mimeMultipart); 
            Transport.send(msg);
            System.out.println("Email Sent With Inline Image Successfully...");

            flag = true; // 

        }catch(Exception e){

            System.out.println("EmailService File Error"+ e);
        }

        return flag; 
    }

}
