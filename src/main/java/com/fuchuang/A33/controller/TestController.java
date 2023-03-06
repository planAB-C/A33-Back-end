package com.fuchuang.A33.controller;

import com.fuchuang.A33.DTO.EmployeeDTO;
import com.fuchuang.A33.entity.LoginEmployee;
import com.fuchuang.A33.mapper.AuthenticationMapper;
import com.fuchuang.A33.utils.EmployeeHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AuthenticationManager authenticationManager ;

    @GetMapping("/SecurityContext")
    public void testSecurityContext(){
        EmployeeDTO emloyee = EmployeeHolder.getEmloyee();
        System.out.println(emloyee);
    }
}
