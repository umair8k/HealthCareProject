package com.hc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hc.entity.EmailRequest;
import com.hc.service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    //this api send simple email
    @PostMapping("/sendingemail")
    public ResponseEntity<?> sendEmail(@RequestBody EmailRequest request)
    {

        System.out.println(request);


        boolean result = this.emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());

        if(result){

            return  ResponseEntity.ok("Email Properly Sent Successfully... ");

        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("email sending fail");
        }
    }
    
   //this api send email with inline image
    @PostMapping("/sendemailinlineimage")
    public ResponseEntity<?> sendEmailWithInlineImage(@RequestBody EmailRequest request)
    {
        System.out.println(request);


        boolean result = this.emailService.sendEmailInlineImage(request.getSubject(), request.getMessage(), request.getTo());

        if(result){

            return  ResponseEntity.ok("Sent Email With Inline Image Successfully... ");

        }else{

            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("inline image email sending fail");
        }
    }

    }

