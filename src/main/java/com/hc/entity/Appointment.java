package com.hc.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "APPOINTMENTS_RECORD")
public class Appointment {


	
	@Id
	@GeneratedValue
	@JsonIgnore
	private int appointmentId;
	
	private String disease;
	
	private String Gender;
	
	private String description;
	@JsonIgnore
	private String regTime;

	private String userName;
	
	private String email;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private String appointmentDateAndTime;
	
	//@ManyToOne(cascade=CascadeType.ALL)
	// @ManyToOne(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	// @JoinColumn(name="user_id", referencedColumnName="user_id")
	//private Patient patient;
	
	//@JsonIgnore
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", disease=" + disease + ", description=" + description
				+ ", regTime=" + regTime + ", userName=" + userName + ", appointmentDateAndTime="
				+ appointmentDateAndTime + ", status=" + status + "]";
	}


	
	
	
}
