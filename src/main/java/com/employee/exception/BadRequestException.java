package com.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)  // 400
public class BadRequestException extends ApiException {
	private static final long serialVersionUID = -2374614452269410814L;

	public BadRequestException(int code, String msg) {
		super(code, msg);
		this.setCode(code);
	}
}