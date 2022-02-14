package com.hc.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class EmailRequest {
	private String to;
	private String subject;
	private String message;

}
