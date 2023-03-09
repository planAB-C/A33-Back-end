package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeService {
    Result login(String email);

    Result regist(String name, String email, String position, String shop_id);
}
