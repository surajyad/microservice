package com.digital.employeegateway.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

	private Long id;
	private String userName;
	private String firstName;
	private String lastName;
	private int age;
}
