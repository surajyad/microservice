package com.digital.employeegateway.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.digital.employeegateway.model.AbstractEmployee;
import com.digital.employeegateway.model.Employee;
import com.digital.employeegateway.service.EmployeeService;

@RestController
public class EmployeeController implements IEmployeeController{
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	private Environment env;
	
	public static final String BUCKET_NAME = "aws.s3.bucket.name";
	public static final String ACCESS_KEY = "aws.access.key";
	public static final String SECRET_KEY = "aws.secret.key";
	
	
	
	@PostMapping("/add-employee")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		return invokeServiceResponse(employee, employeeService::addEmployee);
	}

	@PostMapping("get-employee")
	public ResponseEntity<Employee> getEmployee(@RequestBody AbstractEmployee employee) {
		return invokeServiceResponse(employee, employeeService::getEmployee);
	}
	
	@PostMapping("push-logs")
	public String pushLogs() {
		
		AWSCredentials credentials = new BasicAWSCredentials(
				env.getProperty(ACCESS_KEY), 
				env.getProperty(SECRET_KEY)
				);
		
		AmazonS3 s3client = AmazonS3ClientBuilder
				  .standard()
				  .withCredentials(new AWSStaticCredentialsProvider(credentials))
				  .withRegion(Regions.US_EAST_2)
				  .build();
		
		s3client.putObject(
				env.getProperty(BUCKET_NAME),
				  "logs/applog1.json", 
				  new File("C:\\Users\\surajyad\\Desktop\\terraform-farmer\\microservices\\logs\\app-1576230185870.json")
				);
		return "success";
	}
	
}
