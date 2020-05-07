package com.domen.service;

import java.util.ArrayList;

import com.domen.custom_exception.UsernameNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.domen.dao.EmployeeRepository;
import com.domen.entity.Employee;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	EmployeeRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username)  {
		try {
			Employee user = repository.findByUsername(username);
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
		}catch (Exception e){
			throw new UsernameNotFound("User Not Found");
		}
	}

}
