package com.domen.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.domen.entity.Employee;

public interface EmployeeRepository extends MongoRepository<Employee,String> {
	
	@Query(value = "{'username' : ?0,'password' : ?1}")
	public Employee loginUser(String username,String password);
	
	public Employee findByUsername(String username);
	public void deleteByUsername(String username);

}
