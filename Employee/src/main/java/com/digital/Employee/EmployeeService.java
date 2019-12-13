package com.digital.Employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.digital.Employee.Repository.EmployeeRepositoryImpl;
import com.digital.Employee.vo.Employee;
import com.digital.Employee.vo.GetEmployeeRequest;

@RestController
public class EmployeeService {
	
	@Autowired
	private EmployeeRepositoryImpl employeeRepositoryImpl;
	
	@PostMapping(value = "/get-employee")
	public @ResponseBody Employee getEmployee(@RequestBody   GetEmployeeRequest request) {
		return employeeRepositoryImpl.findByUserNameIgnoreCase(request.getUserName());
	}
	
	@PostMapping(value = "/get-all-employees")
	public @ResponseBody List<Employee> getEmployee(){
		return employeeRepositoryImpl.getAllEmployees();
	}

}
