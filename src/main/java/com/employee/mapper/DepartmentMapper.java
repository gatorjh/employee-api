package com.employee.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.employee.domain.DepartmentDTO;
import com.employee.persistence.Department;

@Mapper(uses = { DeptEmplMapper.class }, componentModel = "spring")
public interface DepartmentMapper {
	Department toDepartment(DepartmentDTO departmentDto);
	List<Department> toDepartments(List<DepartmentDTO> departmentDtos);

	@InheritInverseConfiguration
	DepartmentDTO fromDepartment(Department department);
	@InheritInverseConfiguration
	List<DepartmentDTO> fromDepartments(List<Department> departments);
}
