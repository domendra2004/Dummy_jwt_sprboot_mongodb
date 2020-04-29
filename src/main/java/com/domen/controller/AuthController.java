package com.domen.controller;

import com.domen.entity.AuthRequest;
import com.domen.entity.Employee;
import com.domen.service.EmployeeService;
import com.domen.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"User Authentication Related API"})
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = { "application/json" })
    @ApiOperation(value = "For sign up/register")
    public Employee saveEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
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
