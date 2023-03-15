package com.fuchuang.A33;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fuchuang.A33.entity.*;
import com.fuchuang.A33.mapper.*;
import com.fuchuang.A33.utils.UsualMethodUtils;
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

    @Autowired
    private WorkingMapper workingMapper ;

    @Autowired
    private LocationsMapper locationsMapper ;

    @Autowired
    private TimesMapper timesMapper ;

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

    @Test
    void test5(){
        String s = UsualMethodUtils.parseID("\"20030212\"");
        System.out.println(s);
    }

    @Test
    void test6(){
        String realFlowID = UsualMethodUtils.getRealFlowID("2023-03-17_01");
        System.out.println(realFlowID);
    }

    @Test
    void test7(){
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>());
        for (Employee employee : employees) {
            Times times = new Times(employee.getID(), 0, 0, 0);
            timesMapper.insert(times) ;
        }
    }
}
