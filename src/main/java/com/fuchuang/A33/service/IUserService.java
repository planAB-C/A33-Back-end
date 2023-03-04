package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    public Result Login(String username, String password) ;

    Result Logout(HttpServletRequest request);

}
