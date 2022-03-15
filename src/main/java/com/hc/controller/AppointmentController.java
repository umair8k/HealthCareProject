package com.hc.controller;


import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hc.model.Appointment;
import com.hc.model.AppointmentStatus;
import com.hc.model.EmailRequest;
import com.hc.model.Patient;
import com.hc.repository.AppointmentRepository;
import com.hc.repository.PatientRepository;
import com.hc.service.IEmailService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/post")
@Slf4j
public class AppointmentController {

	@Autowired
	private AppointmentRepository apmntRepository;
	@Autowired
	private PatientRepository repository;
	@Autowired
	private IEmailService emailService;

	
	/*-------@PreAuthorize------- 
	  Using this annotation iam authorising apis 
	*/
	@PostMapping("/create")
	@PreAuthorize("hasRole('ROLE_PATIENT')")// here if logged in user has patient role only he can acces this api api 
	public String bookApmnt(@RequestBody Appointment apmnt, Principal principal){
		log.debug("execution start{}", new Date());
		EmailRequest request =new EmailRequest();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		//repository.findById(id);
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
		log.debug("execution end{}", new Date());
		return principal.getName() + " Your Appointment created successfully , Required Nurse/Doctor Action !";
	}

/*	@GetMapping("/approveAll")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String approveAll() {
		apmntRepository.findAll().stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING)).forEach(appointment -> {
			appointment.setStatus(AppointmentStatus.APPROVED);
			apmntRepository.save(appointment);
		});
		return "Approved all Appointments !";
	}*/

	@GetMapping("/rejectAppointment/{appointId}")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String removeAppointment(@PathVariable int appointId) {
		EmailRequest request =new EmailRequest();
		Appointment apmnt = apmntRepository.findById(appointId).get();
		apmnt.setStatus(AppointmentStatus.REJECTED);
		apmntRepository.save(apmnt);
		request.setSubject("Your Appointment Rejected");
		request.setBody("Your Appointment rejected due to unavoidable circumtances. Inconvenience coused is deeply regretted.XYZ, Please try take take on other date!");
		request.setTo(apmnt.getEmail());
		emailService.sendEmail(request.getSubject(), request.getBody(), request.getTo());
		return "Appointment Rejected !!";
	}


	@GetMapping("/rejectAll")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String rejectAll() {
		apmntRepository.findAll().stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING)).forEach(appointment -> {
			appointment.setStatus(AppointmentStatus.REJECTED); //Stream api for filtering collection data 
			apmntRepository.save(appointment);
		});
		return "Rejected all appointments !";
	}

	@GetMapping("/viewAll")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewAll(){
		return apmntRepository.findAll(); //here iam  not using stream fillter bcz here we dont need to filter, here i am displaying all data 
	}

	@GetMapping("/viewApproved")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewApproved(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.APPROVED))
				.collect(Collectors.toList()); //Stream api for filtering collection data 
	
	}

	@GetMapping("/viewRejected")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewRejected(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.REJECTED))
				.collect(Collectors.toList()); //Stream api for filtering collection data 
	}
	@GetMapping("/viewPending")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewPending(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING))
				.collect(Collectors.toList()); //Stream api for filtering collection data 
	}
	@GetMapping("/getById/{user_id}")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  String getById(Integer id){
		Appointment apmnt=new Appointment();
		Patient patient=repository.findById(id).get();
		return patient+"HII";/*.stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING))
				.collect(Collectors.toList());*/
	}


}
