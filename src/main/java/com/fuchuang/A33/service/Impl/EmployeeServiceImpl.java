package com.fuchuang.A33.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fuchuang.A33.DTO.EmployeeDTO;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.EmployeeRole;
import com.fuchuang.A33.entity.LoginEmployee;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.mapper.EmployeeRoleMapper;
import com.fuchuang.A33.service.IEmployeeService;
import com.fuchuang.A33.utils.Constants;
import com.fuchuang.A33.utils.EmployeeHolder;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper ;

    @Override
    public Result login(String email) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", email));
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email,"");
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(employee)){
            return Result.fail(500,"the email is not belong employee") ;
        }
        String token = UUID.randomUUID().toString();
        //过滤器第一次无法得到的token,需要我们手动去传递
        LoginEmployee loginEmployee = (LoginEmployee) authenticate.getPrincipal();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        //注意不能直接将loginEmployee赋值给employeeDTo，而是应该获取loginEmployee中的Employee部分
        BeanUtil.copyProperties(loginEmployee.getEmployee(),employeeDTO);
        employeeDTO.setPermissions(loginEmployee.getPermissions());
        Map<String, Object> map = BeanUtil.beanToMap(employeeDTO ,
                new HashMap<>() ,
                CopyOptions.create().setIgnoreNullValue(true).setFieldValueEditor((filedName,filedValue)->{
                    if (filedValue == null) {
                        filedValue = "0" ;
                    }else {
                        filedValue = filedValue.toString() ;
                    }
                    return filedValue ;
                }));
        stringRedisTemplate.opsForHash().putAll(Constants.EMPLOYEE_TOKEN + token,map);
        stringRedisTemplate.expire(Constants.EMPLOYEE_TOKEN + token,60*30, TimeUnit.SECONDS) ;
        return Result.success(200,token);
    }

    @Override
    public Result regist(String name, String email, String position, String shopId ,String belong) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", email));
        if(!Objects.isNull(employee)){
            return Result.fail(500,"this email has already registed ");
        }
        Long count = employeeMapper.selectCount(new QueryWrapper<>()) + 1;
        String ID = null ;
        if(count<10) ID = "0" + count ;
        else ID = count.toString() ;
        //如果belong为空，表明没有小组长
        if (belong == null) {
            if(employeeMapper.insert(new Employee(ID, name, email, position, shopId , null ))==0){
                return Result.fail(500,"the system has some wrongs now") ;
            }
        }

        //如果belong不为空，查询并插入
        else {
            Employee group = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", belong));
            if(employeeMapper.insert(new Employee(ID, name, email, position, shopId , group.getID() ))==0){
                return Result.fail(500,"the system has some wrongs now") ;
            }
        }

        //添加默认的员工规则
        EmployeeRole employeeRole = new EmployeeRole();
        employeeRole.setEmployeeID(ID);
        employeeRole.setHobbyType("喜欢的工作日") ;
        employeeRole.setHobbyType("无");
        employeeRoleMapper.insert(employeeRole) ;
        employeeRole.setEmployeeID(ID);
        employeeRole.setHobbyType("工作时间偏好") ;
        employeeRole.setHobbyType("无");
        employeeRoleMapper.insert(employeeRole) ;
        employeeRole.setEmployeeID(ID);
        employeeRole.setHobbyType("班次时长偏好") ;
        employeeRole.setHobbyType("无");
        employeeRoleMapper.insert(employeeRole) ;
        return Result.success(200) ;
    }

    @Override
    public Result changeGroup(String groupEmail) {
        Employee group = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", groupEmail));
        if (group==null){
            return Result.fail(500,"the system has some wrongs now") ;
        }
        if (group.getPosition().equals("小组长")){
            return Result.fail(500,"the email's owner is not '小组长' ") ;
        }
        EmployeeDTO employeeDTO = EmployeeHolder.getEmloyee();
        Employee em = new Employee();
        em.setID(employeeDTO.getID());
        em.setBelong(group.getID());
        employeeMapper.update(em,new QueryWrapper<Employee>().eq("ID",em.getID()));
        return Result.success(200) ;
    }


}
