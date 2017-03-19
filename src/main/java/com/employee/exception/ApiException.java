package com.employee.exception;

public class ApiException extends Exception {
	private static final long serialVersionUID = 469075381612580715L;
	private int code;

	public ApiException(int code, String msg) {
		super(msg);
		this.setCode(code);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}