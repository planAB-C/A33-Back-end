package com.fuchuang.A33;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fuchuang.A33.entity.Authentication;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.EmployeeRole;
import com.fuchuang.A33.mapper.AuthenticationMapper;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.mapper.EmployeeRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private AuthenticationMapper authenticationMapper ;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper ;

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

    @Test
    void test3(){
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Month month = now.getMonth();
        int day = now.getDayOfMonth();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        System.out.println(year);
        System.out.println(month);
        System.out.println(day);
        System.out.println(dayOfWeek);
        System.out.println(format);
    }

    @Test
    void test4(){
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setHobbyValue("无");
        employeeRoleMapper.update(employeeRole,new UpdateWrapper<EmployeeRole>()) ;
    }

}
