package com.bway.springproject.service;

import java.util.List;

import com.bway.springproject.model.Employee;

public interface EmployeeService {

	void addEmployee(Employee emp);
	void deleteEmployee(Long id);
	void update(Employee emp);
	Employee getEmpById(Long id);
	List<Employee> getAllEmployee();
}
