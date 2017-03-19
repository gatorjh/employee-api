package com.employee.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.domain.DepartmentDTO;
import com.employee.exception.ApiException;
import com.employee.exception.BadRequestException;
import com.employee.mapper.DepartmentMapper;
import com.employee.persistence.Department;
import com.employee.repository.DepartmentRepository;

@Service
public class DepartmentService {
	private static final Logger LOG = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	DepartmentMapper departmentMapper;

	public List<DepartmentDTO> findAll() {
		LOG.debug("findAll called");

		List<Department> departments = departmentRepository.findAll();

		return departmentMapper.fromDepartments(departments);
	}

	public DepartmentDTO findOne(Integer id) {
		LOG.debug("findOne called for id {}", id);

		Department department = departmentRepository.findOne(id);

		return departmentMapper.fromDepartment(department);
	}

	public DepartmentDTO save(DepartmentDTO departmentDto) throws ApiException {
		LOG.debug("save called for department {}", departmentDto);

		validateDepartmentDto(departmentDto);

		Department department = departmentMapper.toDepartment(departmentDto);

		try {
			department = departmentRepository.save(department);

			return departmentMapper.fromDepartment(department);
		} catch (Exception e) {
			String msg = "The Department record could not be saved to the database. Message: " + e;
			LOG.error(msg);
			throw new ApiException(500, msg);
		}
	}

	private void validateDepartmentDto(DepartmentDTO departmentDto) throws BadRequestException {
		if(departmentDto == null) {
			throw new BadRequestException(404, "The Department is null.");
		}

		// Validate name
		if(StringUtils.isBlank(departmentDto.getName())) {
			throw new BadRequestException(400, "The name field is required.");
		}
		if(departmentDto.getName().length() > 30) {
			throw new BadRequestException(400, "The maximum field length for the name field is 30.");
		}
	}
}
