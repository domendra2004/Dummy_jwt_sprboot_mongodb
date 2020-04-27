package com.domen.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Collection;

import com.domen.entity.FileDetail;
import com.domen.service.FileService;
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

	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = { "application/json" })
	@ApiOperation(value = "For Adding new Employee")
	public Employee saveEmployee(@RequestBody Employee employee) {
		return employeeService.saveEmployee(employee);
	}

	@DeleteMapping("/deleteUserByUsername")
	@ApiOperation(value = "For deleting Employee using username")
	public void DeleteUserByUserName(@RequestParam("username") String username){
		 employeeService.deleteUserByUsername(username);
	}
	@DeleteMapping("/deleteUserById")
	@ApiOperation(value = "For deleting Employee using id")
	public void DeleteUserById(@RequestParam("id") String id){
		employeeService.deleteUserById(id);
	}


	@GetMapping("/employeeDetails")
	@ApiOperation(value = "Get Employee Details")
	public Collection<Employee> getEmployee() {
		return employeeService.getEmployee();
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
    public ResponseEntity<Object> downloadFile(@RequestParam("path") String path,@RequestParam("filename") String filename) throws IOException
    {
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
	@ApiOperation(value = "Uploaded file details of user")
	public Collection<FileDetail>getUploadedDetailsbyUserName(@RequestParam("username") String username){
		return fileService.getUploadedDetailsbyUserName(username);
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
