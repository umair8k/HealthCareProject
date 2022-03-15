package com.hc.service.impl;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hc.model.Appointment;
import com.hc.model.AppointmentStatus;
import com.hc.model.EmailRequest;
import com.hc.model.Patient;
import com.hc.repository.AppointmentRepository;
import com.hc.repository.PatientRepository;
import com.hc.service.IAppointmentService;
import com.hc.service.IEmailService;

public class AppointmentServiceImpl implements IAppointmentService{
	
	@Autowired
	private AppointmentRepository apmntRepository;
	@Autowired
	private PatientRepository patienbtRepository;
	@Autowired
	private IEmailService emailService;
	
	
@Override
public String createAppoitment(Appointment apmnt, Principal principal, Integer id) {
	EmailRequest request =new EmailRequest();
	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	patienbtRepository.findById(id);
	Date current = new Date();  
	String time=current.toString();
	apmnt.setStatus(AppointmentStatus.PENDING);
	apmnt.setUserName(principal.getName());
	apmnt.setRegTime(time);
	apmntRepository.save(apmnt);
	request.setSubject("Your Appointment is Creates successfully");
	request.setBody("Your Appointment created successfully , Required Nurse/Doctor Action !");
	request.setTo(apmnt.getEmail());
	emailService.sendEmail(request.getSubject(), request.getBody(), request.getTo());
	return principal.getName();
}

}
