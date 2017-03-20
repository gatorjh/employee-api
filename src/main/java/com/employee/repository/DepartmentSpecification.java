package com.employee.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.employee.persistence.Department;
import com.employee.persistence.Department_;

public final class DepartmentSpecification {

	private DepartmentSpecification() {
		throw new IllegalAccessError("Utility class");
	}

	public static Specification<Department> findByCriteria(Department searchCriteria) {
		return new Specification<Department>() {
			@Override
			public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();

				// name
				if(StringUtils.isNotBlank(searchCriteria.getName())) {
					predicates.add(
						criteriaBuilder.equal(root.get(Department_.name), searchCriteria.getName())
					);
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			}
		};
	}
}
