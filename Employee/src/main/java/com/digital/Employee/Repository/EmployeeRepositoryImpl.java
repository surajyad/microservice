package com.digital.Employee.Repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.digital.Employee.vo.Employee;

@Component
public class EmployeeRepositoryImpl {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee findByUserNameIgnoreCase(String userName) {
		return employeeRepository.findByUserName(userName);
	}
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();	
	}
}
