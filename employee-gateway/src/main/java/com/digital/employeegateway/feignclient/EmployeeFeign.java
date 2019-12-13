package com.digital.employeegateway.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.digital.employeegateway.model.AbstractEmployee;
import com.digital.employeegateway.model.Employee;

@FeignClient(name= "employee-service", url = "${endpoint.employee-service}")
public interface EmployeeFeign {

	@PostMapping("/get-employee")
	public @ResponseBody ResponseEntity<Employee> getEmployee(@RequestBody AbstractEmployee employee);
	
	@PostMapping("/add-employee")
	public @ResponseBody ResponseEntity<Employee> addEmployee(@RequestBody Employee employee);
}
