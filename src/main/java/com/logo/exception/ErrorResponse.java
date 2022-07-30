package com.logo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private String message;
	private HttpStatus httpStatus;
	private LocalDateTime dateTime;

	public ErrorResponse(String message, HttpStatus httpStatus, LocalDateTime dateTime) {
		super();
		this.message = message;
		this.httpStatus = httpStatus;
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	@Override
	public String toString() {
		return "ErrorResponse [message=" + message + ", httpStatus=" + httpStatus + ", dateTime=" + dateTime + "]";
	}

}
