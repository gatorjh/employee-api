package com.employee.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firstName;

	private String lastName;

	private Date hireDate;

	@OneToMany(targetEntity=Salary.class, fetch = FetchType.EAGER)
	@JoinColumn(name="emplId")
	private List<Salary> salaries;

	@OneToMany(targetEntity=DeptEmpl.class, fetch = FetchType.EAGER)
	@JoinColumn(name="emplId")
	private List<DeptEmpl> deptEmpls;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public List<Salary> getSalaries() {
		return salaries;
	}

	public void setSalaries(List<Salary> salaries) {
		this.salaries = salaries;
	}

	public List<DeptEmpl> getDeptEmpls() {
		return deptEmpls;
	}

	public void setDeptEmpls(List<DeptEmpl> deptEmpls) {
		this.deptEmpls = deptEmpls;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", hireDate=" + hireDate
				+ "]";
	}
}
