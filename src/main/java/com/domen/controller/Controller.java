package com.domen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Collection;

import com.domen.entity.FileDetail;
import com.domen.service.FileService;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.domen.entity.AuthRequest;
import com.domen.entity.Employee;
import com.domen.service.EmployeeService;
import com.domen.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

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

	@Autowired
	private FileService fileService;

	@GetMapping("/")
	@ApiOperation(value = "Welcome")
	public String welcome() {
		return "Welcome";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = { "application/json" })
	@ApiOperation(value = "For sign up/register")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

	@DeleteMapping("/deleteEmployeeByUsername")
	@ApiOperation(value = "For deleting Employee using username")
	public void DeleteUserByUserName(@RequestParam("username") String username){
		 employeeService.deleteUserByUsername(username);
	}
	@DeleteMapping("/deleteEmployeeById")
	@ApiOperation(value = "For deleting employee using id")
	public void DeleteUserById(@RequestParam("id") String id){
		employeeService.deleteUserById(id);
	}
//

	@PutMapping("/updateEmployee")
	@ApiOperation(value = "For updating mobile number and/or password")
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

	@GetMapping("getEmployeeDetailsByUsername")
	@ApiOperation(value = "UserDetails details using their username")
	public Employee getUserDetails(@RequestParam("username") String username){
		return employeeService.getUserDetails(username);
	}
	@GetMapping("countUploadedDoc")
	@ApiOperation(value = "It will returns number of uploaded document of employee")
	public long countDoc(@RequestParam("username") String username){
		return employeeService.countDoc(username);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ApiOperation(value = "For Upload Document")
	public ResponseEntity<Object> uploadfile(@RequestParam("file") MultipartFile file, HttpServletRequest req) throws IOException {
		String currentToken=req.getHeader("Authorization");
		LocalTime time = LocalTime.now();
		String message="Not Available";
		String username=jwtUtil.extractUsername(currentToken);
		String uploadedTime=time+"";
		String fileName=username+uploadedTime+file.getOriginalFilename();
			if(fileService.saveDocDetails(
					new FileDetail(username,fileDirectory,fileName,uploadedTime))!=null){

				File converFile = new File(fileDirectory ,fileName);
				converFile.createNewFile();
				FileOutputStream fout = new FileOutputStream(converFile);
				fout.write(file.getBytes());
				fout.close();
				message="File is Uploaded successfully";
			}else{
				message="File Not Uploaded";
			}

		return new ResponseEntity<>(message, HttpStatus.OK);
	}

    @RequestMapping(value = "/download", method = RequestMethod.GET)
	@ApiOperation(value = "Download file using path and filename")
    public ResponseEntity<Object> downloadFile(@RequestParam("filename") String filename) throws IOException
    {
        String path="/home/xyz/Springboot_jwt_mongodb/uploaded";

        String downloadableFile =path+"/"+filename ;
        File file = new File(downloadableFile);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                String.format("attachment; filename=\"%s\"", file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);

        return responseEntity;
    }

	@GetMapping("getUploadedFileDetailsByUsername")
	@ApiOperation(value = "Uploaded file details of Employee")
	public Collection<FileDetail>getUploadedDetailsbyUserName(@RequestParam("username") String username){
		return fileService.getUploadedDetailsbyUserName(username);
	}

	@PostMapping("/authenticate")
	@ApiOperation(value = "For Authenticate/login user")
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
