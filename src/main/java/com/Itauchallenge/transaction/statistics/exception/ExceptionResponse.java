package com.Itauchallenge.transaction.statistics.exception;

import java.time.OffsetDateTime;

public class ExceptionResponse {

	private String details;
	private OffsetDateTime timeStamp;
	private String message;
	
	
	public ExceptionResponse(String details, OffsetDateTime timeStamp, String message) {
		
		this.details = details;
		this.timeStamp = timeStamp;
		this.message = message;
		}

	public String getDetails() {
		return details;
	}

	public OffsetDateTime getTimeStamp() {
		return timeStamp;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
