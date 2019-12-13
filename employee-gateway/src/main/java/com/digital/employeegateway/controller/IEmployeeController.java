package com.digital.employeegateway.controller;

import java.util.function.Function;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IEmployeeController {

	default<R, T> ResponseEntity<T> invokeServiceResponse(R clientRequest, Function<R, T> serviceMethod) {
		T response = serviceMethod.apply(clientRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
