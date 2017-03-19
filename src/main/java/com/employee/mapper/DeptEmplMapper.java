package com.employee.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.employee.domain.DeptEmplDTO;
import com.employee.persistence.DeptEmpl;

@Mapper(uses = { EmployeeMapper.class, DepartmentMapper.class }, componentModel = "spring")
public interface DeptEmplMapper {
	@Mapping(target = "fromDate", source = "fromDate", dateFormat = "yyyy-MM-dd")
	@Mapping(target = "toDate", source = "toDate", dateFormat = "yyyy-MM-dd")
	DeptEmpl toDeptEmpl(DeptEmplDTO deptEmplDto);
	List<DeptEmpl> toDeptEmpls(List<DeptEmplDTO> deptEmplDtos);

	@InheritInverseConfiguration
	DeptEmplDTO fromDeptEmpl(DeptEmpl deptEmpl);
	@InheritInverseConfiguration
	List<DeptEmplDTO> fromDeptEmpls(List<DeptEmpl> deptEmpls);
}
