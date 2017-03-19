package com.employee.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.employee.domain.EmployeeDTO;
import com.employee.persistence.Employee;

@Mapper(uses = { SalaryMapper.class, DeptEmplMapper.class }, componentModel = "spring")
public interface EmployeeMapper {
	@Mapping(target = "salaries", source = "salaries")
	@Mapping(target = "deptEmpls", source = "deptEmpls")
	@Mapping(target = "hireDate", source = "hireDate", dateFormat = "yyyy-MM-dd")
	Employee toEmployee(EmployeeDTO employeeDto);
	List<Employee> toEmployees(List<EmployeeDTO> employeeDtos);

	@InheritInverseConfiguration
	EmployeeDTO fromEmployee(Employee employee);
	@InheritInverseConfiguration
	List<EmployeeDTO> fromEmployees(List<Employee> employees);
}