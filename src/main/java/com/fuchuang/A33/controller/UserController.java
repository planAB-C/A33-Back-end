package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.UserServiceImpl;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService ;

    @PostMapping("/login")
    public Result Login(String username , String password){
        Result result = userService.Login(username, password);
        return result;
    }

    @DeleteMapping("/logout")
    public Result Logout(HttpServletRequest request){
        return userService.Logout(request);
    }

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('testq')")
    public String hello(){
        return "hello" ;
    }
}
