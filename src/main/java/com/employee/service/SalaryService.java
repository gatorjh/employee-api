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
import com.employee.domain.SalaryDTO;
import com.employee.exception.ApiException;
import com.employee.exception.BadRequestException;
import com.employee.mapper.SalaryMapper;
import com.employee.persistence.Salary;
import com.employee.repository.SalaryRepository;

@Service
public class SalaryService {
	private static final Logger LOG = LoggerFactory.getLogger(SalaryService.class);

	@Autowired
	private SalaryRepository salaryRepository;

	@Autowired
	SalaryMapper salaryMapper;

	public List<SalaryDTO> findAll() {
		LOG.debug("findAll called");

		List<Salary> salaries = salaryRepository.findAll();

		return salaryMapper.fromSalaries(salaries);
	}

	public SalaryDTO findOne(Integer id) {
		LOG.debug("findOne called for id {}", id);

		Salary salary = salaryRepository.findOne(id);

		return salaryMapper.fromSalary(salary);
	}

	public SalaryDTO save(SalaryDTO salaryDto) throws ApiException {
		LOG.debug("save called for salary {}", salaryDto);

		validateSalaryDto(salaryDto);

		Salary salary = salaryMapper.toSalary(salaryDto);

		try {
			salary = salaryRepository.save(salary);

			return salaryMapper.fromSalary(salary);
		} catch (Exception e) {
			String msg = "The Salary record could not be saved to the database. Message: " + e;
			LOG.error(msg);
			throw new ApiException(500, msg);
		}
	}

	private void validateSalaryDto (SalaryDTO salaryDto) throws BadRequestException {
		if(salaryDto == null) {
			throw new BadRequestException(404, "The Salary is null.");
		}

		// Validate emplId
		if(salaryDto.getEmplId() <= 0) {
			throw new BadRequestException(400, "The emplId field is required.");
		}

		if(salaryDto.getSalary() <= 0) {
			throw new BadRequestException(400, "The salary field is required.");
		}

		// Validate fromDate
		if(StringUtils.isBlank(salaryDto.getFromDate())) {
			throw new BadRequestException(400, "The fromDate field is required.");
		}
		try {
			DateUtils.parseDate(salaryDto.getFromDate(), Constants.HIRE_DATE_FORMAT);
		} catch (ParseException e) {
			throw new BadRequestException(400, "The fromDate must be a valid date. i.e. " + Constants.HIRE_DATE_FORMAT);
		}

		// Validate toDate. Can be null.
		if(StringUtils.isNotBlank(salaryDto.getToDate())) {
			try {
				DateUtils.parseDate(salaryDto.getToDate(), Constants.HIRE_DATE_FORMAT);
			} catch (ParseException e) {
				throw new BadRequestException(400, "The toDate must be a valid date. i.e. " + Constants.HIRE_DATE_FORMAT);
			}
		}
	}
}
