package com.employee.service;

import java.text.ParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.common.Constants;
import com.employee.domain.EmployeeDTO;
import com.employee.exception.ApiException;
import com.employee.exception.BadRequestException;
import com.employee.exception.NotFoundException;
import com.employee.mapper.EmployeeMapper;
import com.employee.persistence.Employee;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	EmployeeMapper employeeMapper;

	public List<EmployeeDTO> findAll() {
		LOG.debug("findAll called");

		List<Employee> employees = employeeRepository.findAll();

		return employeeMapper.fromEmployees(employees);
	}

	public EmployeeDTO findOne(Integer id) {
		LOG.debug("findOne called for id {}", id);

		Employee employee = employeeRepository.findOne(id);

		return employeeMapper.fromEmployee(employee);
	}

	public void delete(Integer id) throws ApiException {
		LOG.debug("delete called for id {}", id);

		if(!employeeRepository.exists(id)) {
			throw new NotFoundException(404, "An employee with id '" + id + "' was not found");
		}

		try {
			employeeRepository.delete(id);
		} catch (Exception e) {
			LOG.error("An exception was thrown while deleting an Employee with id '" + id + "'. Message: " + e);
		}
	}

	public EmployeeDTO save(EmployeeDTO employeeDto) throws ApiException {
		LOG.debug("save called for employee {}", employeeDto);

		validateEmployeeDto(employeeDto);

		Employee employee = employeeMapper.toEmployee(employeeDto);

		try {
			employee = employeeRepository.save(employee);

			return employeeMapper.fromEmployee(employee);
		} catch (Exception e) {
			String msg = "The Employee record could not be saved to the database. Message: " + e;
			LOG.error(msg);
			throw new ApiException(500, msg);
		}
	}

	private void validateEmployeeDto (EmployeeDTO employeeDto) throws BadRequestException {
		if(employeeDto == null) {
			throw new BadRequestException(404, "The Employee is null.");
		}

		// Validate firstName
		if(StringUtils.isBlank(employeeDto.getFirstName())) {
			throw new BadRequestException(400, "The firstName field is required.");
		}
		if(employeeDto.getFirstName().length() > 14) {
			throw new BadRequestException(400, "The maximum field length for the firstName field is 14.");
		}

		// Validate lastName
		if(StringUtils.isBlank(employeeDto.getLastName())) {
			throw new BadRequestException(400, "The lastName field is required.");
		}
		if(employeeDto.getLastName().length() > 16) {
			throw new BadRequestException(400, "The maximum field length for the lastName field is 16.");
		}

		// Validate hireDate
		if(StringUtils.isBlank(employeeDto.getHireDate())) {
			throw new BadRequestException(400, "The hireDate field is required.");
		}
		try {
			DateUtils.parseDate(employeeDto.getHireDate(), Constants.HIRE_DATE_FORMAT);
		} catch (ParseException e) {
			throw new BadRequestException(400, "The hireDate must be a valid date. i.e. " + Constants.HIRE_DATE_FORMAT);
		}
	}
}
