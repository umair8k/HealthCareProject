package com.hc.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor 
public class EmailResponse {

	private boolean success;
	
	private String jobId;
	
	private String jobGroup;
	
	private String message;
	
	public EmailResponse(boolean success, String message) {
		this.success=success;
		this.message=message;
	}

}
