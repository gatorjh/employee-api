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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.domain.DepartmentDTO;
import com.employee.exception.ApiException;
import com.employee.exception.NotFoundException;
import com.employee.persistence.Department;
import com.employee.service.DepartmentService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rest")
public class DepartmentController {
	private static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

	@Autowired
	DepartmentService departmentService;

	@ApiOperation(value = "Retrieve all Department's", notes = "")
	@RequestMapping(value = "/departments", method = GET, produces="application/json")
	@ResponseBody
	public List<DepartmentDTO> departments(HttpServletResponse response,
			@ApiParam(value = "The department's 'name'", required = false) @RequestParam(value="name", required = false, defaultValue="") String name,
			@ApiParam(value = "Limit the amount of records to retrieve", required = false) @RequestParam(value="limit", required = false, defaultValue="100") Integer limit,
			@ApiParam(value = "Skip the number of records specified", required = false) @RequestParam(value="offset", required = false, defaultValue="0") Integer offset,
			@ApiParam(value = "Order by", required = false, allowableValues="name") @RequestParam(value="orderBy", required = false, defaultValue="name") String orderBy,
			@ApiParam(value = "Order", required = false, allowableValues="ASC, DESC") @RequestParam(value="order", required = false, defaultValue="ASC") String order) {

		Department searchCriteria = new Department();
		searchCriteria.setName(name);
		
		LOG.debug("Processing /departments GET request with criteria: {}", searchCriteria);

		return departmentService.findByCriteria(searchCriteria, limit, offset, orderBy, order);
	}

	@ApiOperation(value = "Retrieve a specific Department by its 'id'", notes = "")
	@RequestMapping(value = "/departments/{id}", method = GET, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 404, message = "Not Found") })
	@ResponseBody
	public DepartmentDTO departmentById(
			HttpServletResponse response,
			@ApiParam(value = "The 'id'", required = true) @PathVariable Integer id) throws NotFoundException {

		LOG.debug("Processing /departmentById GET request for id: {}", id);

		DepartmentDTO department = departmentService.findOne(id);

		if(department == null) {
			throw new NotFoundException(404, "A department with id '" + id + "' was not found");
		}

		return department;
	}

	@ApiOperation(value = "Insert a department", notes = "")
	@RequestMapping(value = "/departments/", method = POST, produces="application/json")
	@ApiResponses(value = { @ApiResponse(code = 500, message = "Internal Server Error"), @ApiResponse(code = 400, message = "Bad Request") })
	@ResponseBody
	public DepartmentDTO departmentPost(
			HttpServletResponse response,
			@ApiParam(value = "The Department record to insert", required = true) @RequestBody DepartmentDTO department) throws ApiException {

		LOG.debug("Processing /departmentPost POST request with Department: {}", department);

		return departmentService.save(department);
	}
}
