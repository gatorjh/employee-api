package com.employee.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.domain.SalaryDTO;
import com.employee.exception.ApiException;
import com.employee.exception.NotFoundException;
import com.employee.service.SalaryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rest")
public class SalaryController {
	private static final Logger LOG = LoggerFactory.getLogger(SalaryController.class);

	@Autowired
	SalaryService salaryService;

	@ApiOperation(value = "Retrieve all Salaries", notes = "")
	@RequestMapping(value = "/salaries", method = GET, produces="application/json")
	@ResponseBody
	public List<SalaryDTO> salaries(
			HttpServletResponse response) {

		LOG.debug("Processing /salaries GET request");

		return salaryService.findAll();
	}

	@ApiOperation(value = "Retrieve a specific Salary by its 'id'", notes = "")
	@RequestMapping(value = "/salaries/{id}", method = GET, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public SalaryDTO salaryById(
			HttpServletResponse response,
			@ApiParam(value = "The 'id'", required = true) @PathVariable Integer id) throws NotFoundException {

		LOG.debug("Processing /salaryById GET request for id: {}", id);

		SalaryDTO salary = salaryService.findOne(id);

		if(salary == null) {
			throw new NotFoundException(404, "A salary with id '" + id + "' was not found");
		}

		return salary;
	}

	@ApiOperation(value = "Insert a salary", notes = "")
	@RequestMapping(value = "/salaries/", method = POST, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 400, message = "Bad Request") })
	@ResponseBody
	public SalaryDTO salaryPost(
			HttpServletResponse response,
			@ApiParam(value = "The Salary record to insert", required = true) @RequestBody SalaryDTO salary) throws ApiException {

		LOG.debug("Processing /salaryPost POST request with Salary: {}", salary);

		return salaryService.save(salary);
	}
}
