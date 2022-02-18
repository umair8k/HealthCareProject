package com.hc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hc.model.Patient;
import com.hc.repository.PatientRepository;

@Service
public class PatientDetailsService implements UserDetailsService {

	@Autowired
	private PatientRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Patient> patient = repository.findByUserName(username);
		return patient.map(PatientDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
	}
//	  public boolean canAccessUser(Appointment appointment, String userName) {
//	      //  LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
//	        return appointment != null
//	                && ( appointment.getUserName().equals(userName));
//	}
}
