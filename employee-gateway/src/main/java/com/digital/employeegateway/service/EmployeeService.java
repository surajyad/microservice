package com.digital.employeegateway.service;

import java.util.List;

import com.digital.employeegateway.model.AbstractEmployee;
import com.digital.employeegateway.model.Employee;

public interface EmployeeService {

	public Employee getEmployee(AbstractEmployee employee);
	
	public List<Employee> getAllEmployees();
	
	public Employee addEmployee(Employee employee);
	
}
