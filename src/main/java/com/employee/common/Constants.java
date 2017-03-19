package com.employee.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class Constants {
	public static final String HIRE_DATE_FORMAT = "yyyy-MM-dd";
	public static final DateFormat HIRE_DATE_FORMATTER = new SimpleDateFormat(HIRE_DATE_FORMAT);


	private Constants() {
		throw new IllegalAccessError("Constants class");
	}
}
