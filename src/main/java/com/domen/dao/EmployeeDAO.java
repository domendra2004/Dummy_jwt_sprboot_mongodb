package com.domen.dao;

import java.util.Collection;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.domen.entity.Employee;


@Component
public class EmployeeDAO {
	@Autowired
	EmployeeRepository repository;
	@Autowired
	MongoOperations mongoOperations;
	@Autowired
	private BCryptPasswordEncoder encoder;

	public Collection<Employee> getEmployee(){
		return repository.findAll();
	}

	public Employee saveEmployee(Employee employee) {
		employee.setPassword(encoder.encode(employee.getPassword()));
		return repository.insert(employee);
	}

	public Employee loginUser(String username ,String password) {
		return repository.loginUser(username, password);
	}

	public void deleteById(String id){
		repository.deleteById(id);
	}

	public Employee getUserDetails(String username){
		return repository.findByUsername(username);
	}
	public UpdateResult updateEmployee(String username,String password,String mobile){

		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(username));
		Update update = new Update();
		if(password!=null)
			update.set("password",encoder.encode(password));
		if(mobile!=null)
			update.set("mobile",mobile);
	return 	mongoOperations.upsert(query, update, Employee.class);

	}



}
