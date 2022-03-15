package com.hc.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

	List<Appointment> findByUserName(String userName);

	}
