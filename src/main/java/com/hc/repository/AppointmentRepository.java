package com.hc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hc.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

	List<Appointment> findByUserName(String userName);
	 // List<Appointment> findByUserName(String username);
	@Modifying
	@Query("update Appointment u set u.disease = :disease where u.id = :id")
	void updatePhone(@Param(value = "id") Integer id, @Param(value = "disease") String disease);
	

}
