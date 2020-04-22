package com.domen.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.domen.dao.EmployeeDAO;
import com.domen.entity.Employee;


@Service
public class EmployeeService {
	@Autowired
	private EmployeeDAO  employeeDAO;

	public Collection<Employee> getEmployee(){
		return employeeDAO.getEmployee();
	}

	public Employee saveEmployee(Employee employee) {	
		return employeeDAO.saveEmployee(employee);
	}

	public Employee loginUser(String username,String password) {
		//Example<Employee> e=Example.of(employee);
		return employeeDAO.loginUser(username,password);
	}
	
		
}
