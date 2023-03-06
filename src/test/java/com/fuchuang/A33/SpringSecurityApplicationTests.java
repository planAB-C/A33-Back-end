package com.fuchuang.A33;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.Authentication;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.mapper.AuthenticationMapper;
import com.fuchuang.A33.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private AuthenticationMapper authenticationMapper ;

    @Test
    void test1(){
        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>().eq("id", 1);
        Employee employee = employeeMapper.selectOne(wrapper);
        System.out.println(employee);
    }

    @Test
    void test2(){
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", "2359643054@qq.com"));
        ArrayList<Authentication> authentications = authenticationMapper.getAuthenticationsByPosition(employee.getID(), employee.getPosition());
        for (Authentication authentication : authentications) {
            System.out.println(authentication.getAuthentication());
        }
    }

}
