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

import com.hc.entity.Appointment;
import com.hc.entity.AppointmentStatus;
import com.hc.entity.EmailRequest;
import com.hc.entity.Patient;
import com.hc.repository.AppointmentRepository;
import com.hc.repository.PatientRepository;
import com.hc.service.EmailService;


@RestController
@RequestMapping("/post")
public class AppointmentController {
	
	@Autowired
	private AppointmentRepository apmntRepository;
	@Autowired
	private PatientRepository repository;
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/create/{id}")
	@PreAuthorize("hasRole('ROLE_PATIENT')")
	public String createApmnt(@RequestBody Appointment apmnt, Principal principal,@PathVariable Integer id){
		Patient patient=new Patient();
		EmailRequest request =new EmailRequest();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		repository.findById(id);
		Date current = new Date();  
		String time=current.toString();
		apmnt.setStatus(AppointmentStatus.PENDING);
		apmnt.setUserName(principal.getName());
		apmnt.setRegTime(time);
		//apmnt.setPatient(patient);
		//patient.setId();
		apmntRepository.save(apmnt);
		request.setSubject("Your Appointment is Creates successfully");
		request.setMessage("Your Appointment created successfully , Required Nurse/Doctor Action !");
		request.setTo(apmnt.getEmail());
		emailService.sendEmail(request.getSubject(), request.getMessage(), request.getTo());
		return principal.getName() + " Your Appointment created successfully , Required Nurse/Doctor Action !";
	}
	
	@GetMapping("/getChildByParent/{id}")
	public String getRecordByParent(int id) {
		Optional<Patient> opt=repository.findById(id);
		if(opt.isPresent()) {
			List<Appointment> child=opt.get().getAppointment();
			apmntRepository.findAll();
			return child+"here is child data";
		}else {
			return "child not found:";
		}
	}
	
	
//	@GetMapping("/createsss/{id}")
//	@PreAuthorize("hasRole('ROLE_PATIENT')")
//	public String createApmnt(@RequestBody Appointment apmnt, Principal principal,@PathVariable int id ){
//		Optional<Patient> opt=repository.findById(id);
//		if(opt.isPresent()){
//			//get all child
//			List<Appointment> child=opt.get().getAppointment();
////		   Patient patient=new Patient();
////			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
////			Date current = new Date();  
////			String time=current.toString();
////			apmnt.setStatus(AppointmentStatus.PENDING);
////			apmnt.setUserName(principal.getName());
////			apmnt.setRegTime(time);
////			//apmnt.setPatient(patient);
////			apmntRepository.save(apmnt);
//			return child+ "All childs(appointment) of a patient are added";
//		}
//		 else {
//			  return  principal.getName() +"Person not found";
//		  }
//	}
//	
	/*@GetMapping("/get")
	public void getchild() {
		List<Patient> list=repository.findAll();
		   list.forEach(per->{
		    	
		    	 //get childs of each parent
		    	 List<Appointment>  childs=per.getAppointment();
		    	 childs.forEach(ph->{
		  
		    	 });
		     });
	}
	

	@GetMapping("/approveAppointment/{appointId}")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String approveApoint(@PathVariable int appointId) {
		Appointment apmnt = apmntRepository.findById(appointId).get();
		apmnt.setStatus(AppointmentStatus.APPROVED);
		apmntRepository.save(apmnt);
		return "Appointment Approved !!";
	}
	@PostMapping("/getChild/{id}")
	public String getChild(int id, Principal principal,Appointment apmnt) {
		//loading parent
		Optional<Patient> opt=repository.findById(id);
		if(opt.isPresent()){
			//get all child
			List<Appointment> child=opt.get().getAppointment();
			Patient patient=new Patient();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			repository.findById(id);
			Date current = new Date();  
			String time=current.toString();
			apmnt.setStatus(AppointmentStatus.PENDING);
		//	apmnt.setUserName(principal.getName());
			apmnt.setRegTime(time);
			//apmnt.setPatient(patient);
			apmntRepository.findAll();
			return child+"all child";
		} 
		
		else {
			  return  "Person not found";
		}
	}

/*	@GetMapping("/getStatus/{userName}")
	//@PreAuthorize("principal.userName")
@PreAuthorize("authentication.principal.equals(#userName) ")
	//@PreAuthorize("hasRole('ROLE_PATIENT') and authentication.principal.getName(#userName)")
	
	public List<Appointment> loadUserByUsername(@RequestBody  String userName){
		List<Appointment> patient =apmntRepository.findByUserName(userName);
		Appointment apmnt=new Appointment();
	
		return patient;
	
			
	}*/
	
	
//	@RequestMapping(value = "/userName", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Appointment> currentUserName(Principal principal) {
//		List<Appointment> patient =principal.getName()
//        return patient;
//    }

	@GetMapping("/approveAll")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String approveAll() {
		apmntRepository.findAll().stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING)).forEach(appointment -> {
			appointment.setStatus(AppointmentStatus.APPROVED);
			apmntRepository.save(appointment);
		});
		return "Approved all Appointments !";
	}

	@GetMapping("/rejectAppointment/{appointId}")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String removeAppointment(@PathVariable int appointId) {
		Appointment apmnt = apmntRepository.findById(appointId).get();
		apmnt.setStatus(AppointmentStatus.REJECTED);
		apmntRepository.save(apmnt);
		return "Appointment Rejected !!";
	}


	@GetMapping("/rejectAll")
	@PreAuthorize("hasAuthority('ROLE_DOCTOR')")
	public String rejectAll() {
		apmntRepository.findAll().stream().filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING)).forEach(appointment -> {
			appointment.setStatus(AppointmentStatus.REJECTED);
			apmntRepository.save(appointment);
		});
		return "Rejected all appointments !";
	}

	@GetMapping("/viewAll")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewAll(){
		return apmntRepository.findAll();
	}

	@GetMapping("/viewApproved")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewApproved(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.APPROVED))
				.collect(Collectors.toList());
	}

	@GetMapping("/viewRejected")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewRejected(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.REJECTED))
				.collect(Collectors.toList());
	}
	@GetMapping("/viewPending")
	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  List<Appointment> viewPending(){
		return apmntRepository.findAll().stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING))
				.collect(Collectors.toList());
	}
	@GetMapping("/getById/{user_id}")
//	@PreAuthorize("hasAuthority('ROLE_NURSE') or hasAuthority('ROLE_DOCTOR') or hasAuthority('ROLE_ADMIN')")
	public  String getById(Integer id){
		Appointment apmnt=new Appointment();
		Patient patient=repository.findById(id).get();
		return patient+"HII";/*.stream()
				.filter(appointment -> appointment.getStatus().equals(AppointmentStatus.PENDING))
				.collect(Collectors.toList());*/
	}
	

}
