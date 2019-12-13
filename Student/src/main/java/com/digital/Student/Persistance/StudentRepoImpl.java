package com.digital.Student.Persistance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class StudentRepoImpl {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public Student getStudentByName(String name) {
		return studentRepository.getStudentByName(name);
	}

	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	public Student addStudent(Student student) {
		return studentRepository.save(student);
	}

	public String deleteStudent(Student student) {
		 studentRepository.delete(student);
		 return "deleted successfully";
	}
}
