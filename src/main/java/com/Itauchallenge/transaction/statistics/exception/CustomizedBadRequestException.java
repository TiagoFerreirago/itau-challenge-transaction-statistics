package com.Itauchallenge.transaction.statistics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomizedBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomizedBadRequestException(String msg) {
		super(msg);
	}
}
