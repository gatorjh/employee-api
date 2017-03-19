package com.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND)  // 404
public class NotFoundException extends ApiException {
	private static final long serialVersionUID = -4926340274864261074L;

	public NotFoundException(int code, String msg) {
		super(code, msg);
		this.setCode(code);
	}
}