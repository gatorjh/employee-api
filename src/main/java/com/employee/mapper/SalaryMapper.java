package com.employee.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.employee.domain.SalaryDTO;
import com.employee.persistence.Salary;

@Mapper(uses = { EmployeeMapper.class, DeptEmplMapper.class }, componentModel = "spring")
public interface SalaryMapper {
	@Mapping(target = "fromDate", source = "fromDate", dateFormat = "yyyy-MM-dd")
	@Mapping(target = "toDate", source = "toDate", dateFormat = "yyyy-MM-dd")
	Salary toSalary(SalaryDTO salaryDto);
	List<Salary> toSalaries(List<SalaryDTO> salaryDtos);

	@InheritInverseConfiguration
	SalaryDTO fromSalary(Salary salary);
	@InheritInverseConfiguration
	List<SalaryDTO> fromSalaries(List<Salary> salaries);
}
