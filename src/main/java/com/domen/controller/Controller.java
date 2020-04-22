package com.domen.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.domen.entity.AuthRequest;
import com.domen.entity.Employee;
import com.domen.service.EmployeeService;
import com.domen.util.JwtUtil;

@RestController
@Api(tags = {"Basic Operation related api"})
public class Controller {
public static String fileDirectory = System.getProperty("user.dir") + "/uploaded";
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/")
	@ApiOperation(value = "Welcome")
	public String welcome() {
		return "Welcome";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "application/json" })
	@ApiOperation(value = "For Adding new Employee")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

	@GetMapping("/employeeDetails")
	@ApiOperation(value = "Get Employee Details")
	public Collection<Employee> getEmployee() {
		return employeeService.getEmployee();
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "For Upload Document")
	public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile file) throws IOException {
		File converFile = new File(fileDirectory ,file.getOriginalFilename());
		converFile.createNewFile();
		FileOutputStream fout = new FileOutputStream(converFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("File is Uploaded successfully", HttpStatus.OK);
	}

	@PostMapping("/authenticate")
	@ApiOperation(value = "For Authenticate user")
	public String generateToken(@RequestBody AuthRequest authRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception e) {
			throw new Exception("Invalid Username or password");
		}
		return jwtUtil.generateToken(authRequest.getUsername());
	}

}
