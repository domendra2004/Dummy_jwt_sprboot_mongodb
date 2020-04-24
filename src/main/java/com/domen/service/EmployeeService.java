package com.domen.service;

import java.util.Collection;

import com.domen.entity.FileDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public FileDetail saveDocDetails(FileDetail fileDetail) {
	return  employeeDAO.saveDocDetails(fileDetail);
    }
}
