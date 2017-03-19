package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.employee.persistence.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer>, JpaSpecificationExecutor<Salary> {

}
