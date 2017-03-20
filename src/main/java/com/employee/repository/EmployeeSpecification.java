package com.employee.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.employee.persistence.Employee;
import com.employee.persistence.Employee_;

public final class EmployeeSpecification {

	private EmployeeSpecification() {
		throw new IllegalAccessError("Utility class");
	}

	public static Specification<Employee> findByCriteria(Employee searchCriteria) {
		return new Specification<Employee>() {
			@Override
			public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				// First name
				if(StringUtils.isNotBlank(searchCriteria.getFirstName())) {
					predicates.add(
						criteriaBuilder.equal(root.get(Employee_.firstName), searchCriteria.getFirstName())
					);
				}

				// Last name
				if(StringUtils.isNotBlank(searchCriteria.getLastName())) {
					predicates.add(
						criteriaBuilder.equal(root.get(Employee_.lastName), searchCriteria.getLastName())
					);
				}

				// Hire date
				if(searchCriteria.getHireDate() != null) {
					predicates.add(
						criteriaBuilder.greaterThanOrEqualTo(root.get(Employee_.hireDate), searchCriteria.getHireDate())
					);
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
}
