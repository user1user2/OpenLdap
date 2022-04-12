package com.openldap.ldap.error;

import java.util.Date;

import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExceptionDetails {
	
	private Date timetamp;
	private String message;
	private String details;
	public ExceptionDetails(Date timetamp, String message, String details) {
		super();
		this.timetamp = timetamp;
		this.message = message;
		this.details = details;
	}
	
	public Date getTimetamp() {
		return timetamp;
	}
	public void setTimetamp(Date timetamp) {
		this.timetamp = timetamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	

}
