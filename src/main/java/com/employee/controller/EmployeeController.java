package com.employee.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.common.Constants;
import com.employee.domain.EmployeeDTO;
import com.employee.exception.ApiException;
import com.employee.exception.NotFoundException;
import com.employee.persistence.Employee;
import com.employee.service.EmployeeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rest")
public class EmployeeController {
	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@CrossOrigin
	@ApiOperation(value = "Retrieve all Employee's", notes = "")
	@RequestMapping(value = "/employees", method = {GET,OPTIONS}, produces="application/json")
	@ResponseBody
	public List<EmployeeDTO> employees(HttpServletResponse response,
			@ApiParam(value = "The employee's 'firstName'", required = false) @RequestParam(value="firstName", required = false, defaultValue="") String firstName,
			@ApiParam(value = "The employee's 'lastName'", required = false) @RequestParam(value="lastName", required = false, defaultValue="") String lastName,
			@ApiParam(value = "The 'hireDate'. Great than or equal to. Format yyyy-MM-dd", required = false) @RequestParam(value="hireDate", required = false, defaultValue="") String hireDate,
			@ApiParam(value = "Limit the amount of records to retrieve", required = false) @RequestParam(value="limit", required = false, defaultValue="100") Integer limit,
			@ApiParam(value = "Skip the number of records specified", required = false) @RequestParam(value="offset", required = false, defaultValue="0") Integer offset,
			@ApiParam(value = "Order by", required = false, allowableValues="firstName, lastName, hireDate") @RequestParam(value="orderBy", required = false, defaultValue="hireDate") String orderBy,
			@ApiParam(value = "Order", required = false, allowableValues="ASC, DESC") @RequestParam(value="order", required = false, defaultValue="ASC") String order) {

		Employee searchCriteria = new Employee();
		searchCriteria.setFirstName(firstName);
		searchCriteria.setLastName(lastName);
		if(StringUtils.isNotBlank(hireDate)) {
			try {
				searchCriteria.setHireDate(Constants.HIRE_DATE_FORMATTER.parse(hireDate));
			} catch (ParseException e) {
				LOG.error("The hireDate threw an expcetion. Ignoring. Message {}" , e);
			}
		}

		LOG.debug("Processing /employees GET request with criteria: {}", searchCriteria);

		return employeeService.findByCriteria(searchCriteria, limit, offset, orderBy, order);
	}
/*
	@ApiOperation(value = "Delete a specific Employee by its 'id'", notes = "")
	@RequestMapping(value = "/employees/{id}", method = DELETE, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public void employeeDelete(
			HttpServletResponse response,
			@ApiParam(value = "The 'id'", required = true) @PathVariable Integer id) throws ApiException {

		LOG.debug("Processing /employeeDelete DELETE request for id: {}", id);

		employeeService.delete(id);
	}
*/
	@ApiOperation(value = "Retrieve a specific Employee by its 'id'", notes = "")
	@RequestMapping(value = "/employees/{id}", method = GET, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public EmployeeDTO employeeById(
			HttpServletResponse response,
			@ApiParam(value = "The 'id'", required = true) @PathVariable Integer id) throws NotFoundException {

		LOG.debug("Processing /employeeById GET request for id: {}", id);

		EmployeeDTO employee = employeeService.findOne(id);

		if(employee == null) {
			throw new NotFoundException(404, "An employee with id '" + id + "' was not found");
		}

		return employee;
	}

	@ApiOperation(value = "Insert an employee", notes = "")
	@RequestMapping(value = "/employees/", method = POST, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 400, message = "Bad Request") })
	@ResponseBody
	public EmployeeDTO employeePost(
			HttpServletResponse response,
			@ApiParam(value = "The Employee record to insert", required = true) @RequestBody EmployeeDTO employee) throws ApiException {

		LOG.debug("Processing /employeePost POST request with Employee: {}", employee);

		return employeeService.save(employee);
	}

	@ApiOperation(value = "Update an employee", notes = "")
	@RequestMapping(value = "/employees/{id}", method = PUT, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public EmployeeDTO employeePut(
			HttpServletResponse response,
			@ApiParam(value = "The 'id'", required = true) @PathVariable Integer id,
			@ApiParam(value = "The Employee record to update", required = true) @RequestBody EmployeeDTO employee) throws ApiException {

		LOG.debug("Processing /employeePut PUT request with id {} and Employee: {}", id, employee);

		EmployeeDTO employeeResponse = employeeService.findOne(id);
		if(employeeResponse == null) {
			throw new NotFoundException(404, "An employee with id '" + id + "' was not found");
		}

		employee.setId(id);

		return employeeService.save(employee);
	}
}
