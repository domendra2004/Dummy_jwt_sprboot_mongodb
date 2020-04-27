package com.domen.service;

import java.util.Collection;

import com.domen.entity.FileDetail;
import com.mongodb.client.result.UpdateResult;
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
	public UpdateResult updateEmployee(String username, String password, String mobile){
		return employeeDAO.updateEmployee(username,password,mobile);
	}

	public Employee loginUser(String username,String password) {
		//Example<Employee> e=Example.of(employee);
		return employeeDAO.loginUser(username,password);
	}
	public void deleteUserByUsername(String username){
		employeeDAO.deleteUserByName(username);
	}
	public void deleteUserById(String id){
		employeeDAO.deletebyId(id);
	}


	public Employee getUserDetails(String username) {
		return employeeDAO.getUserDetails(username);
	}

	public long countDoc(String username) {
		return employeeDAO.countDoc(username);
	}
}
