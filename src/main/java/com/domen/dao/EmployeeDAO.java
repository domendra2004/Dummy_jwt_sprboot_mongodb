package com.domen.dao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.domen.entity.Employee;


@Component
public class EmployeeDAO {
	@Autowired
	EmployeeRepository repository;

	public Collection<Employee> getEmployee(){
		return repository.findAll();
	}

	public Employee saveEmployee(Employee employee) {
		return repository.insert(employee);
	}

	public Employee loginUser(String username ,String password) {
		Employee employee1=repository.loginUser(username, password);
		return employee1;
				
	}
}
