package com.digital.Student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.digital.Student.Persistance.Student;
import com.digital.Student.Persistance.StudentRepoImpl;
import com.digital.Student.vo.AbstractStudent;

@RestController
public class StudentController {

	@Autowired
	private StudentRepoImpl studentRepoImpl;
	
	@PostMapping(value = "/get-student")
	public @ResponseBody Student getStudentByName( @RequestBody AbstractStudent student ) {
		return studentRepoImpl.getStudentByName(student.getName());
	}
	
	@PostMapping(value = "/get-all-students")
	public @ResponseBody List<Student> getAllStudents() {
		return studentRepoImpl.getAllStudents();
	}
	
	@PostMapping(value = "/add-student")
	public @ResponseBody Student addStudent( @RequestBody Student student ) {
		return studentRepoImpl.addStudent(student);
	}
	
	@PostMapping(value = "/delete-student")
	public @ResponseBody String deleteStudent( @RequestBody Student student ) {
		return studentRepoImpl.deleteStudent(student);
	}
}
