package com.digital.employeegateway.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digital.employeegateway.feignclient.EmployeeFeign;
import com.digital.employeegateway.model.AbstractEmployee;
import com.digital.employeegateway.model.Employee;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeFeign employeeFeign;

	@Override
	public Employee getEmployee(AbstractEmployee employee) {
		ResponseEntity<Employee> employeeResponse = employeeFeign.getEmployee(employee);
		return employeeResponse.getBody();
	}

	@Override
	public List<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee addEmployee(Employee employee) {
		ResponseEntity<Employee> employeeResponse = employeeFeign.addEmployee(employee);
		return employeeResponse.getBody();
	}

}
