package com.domen.controller;
import java.util.Collection;
import com.domen.service.FileService;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.domen.entity.Employee;
import com.domen.service.EmployeeService;
import com.domen.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"Employee Related API"})
public class EmployeeController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private FileService fileService;

	@GetMapping("/")
	@ApiOperation(value = "Welcome")
	public String welcome() {
		return "Welcome";
	}

	@DeleteMapping("/deleteEmployeeById")
	@ApiOperation(value = "For deleting employee using id")
	public void deleteUserById(@RequestParam("id") String id){
		employeeService.deleteUserById(id);
	}

	@PutMapping("/updateEmployee")
	@ApiOperation(value = "For updating current loged in user (mobile number and/or password ) ")

	public UpdateResult updateEmployee(@RequestParam(required = false) String password, @RequestParam(required = false) String mobile,
									   HttpServletRequest req) {
	String username=jwtUtil.extractUsername(req.getHeader("Authorization"));
		return employeeService.updateEmployee(username,password,mobile);
	}

	@GetMapping("/allEmployeeDetails")
	@ApiOperation(value = "Get all employee details")
	public Collection<Employee> getEmployee() {
		return employeeService.getEmployee();
	}

	@GetMapping("/getEmployeeDetailsByUsername")
	@ApiOperation(value = "UserDetails details using their username")
	public Employee getUserDetails(@RequestParam("username") String username){
		return employeeService.getUserDetails(username);
	}

}
