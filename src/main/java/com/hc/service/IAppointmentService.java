package com.hc.service;

import java.security.Principal;
import java.util.List;

import com.hc.model.Appointment;

public interface IAppointmentService {
	
	public String createAppoitment(Appointment apmt, Principal principal, Integer id);

}
