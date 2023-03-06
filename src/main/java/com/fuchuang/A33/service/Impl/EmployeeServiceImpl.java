package com.fuchuang.A33.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.resource.StringResource;
import cn.hutool.core.lang.UUID;
import cn.hutool.system.RuntimeInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.DTO.EmployeeDTO;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.LoginEmployee;
import com.fuchuang.A33.mapper.AuthenticationMapper;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.service.EmployeeService;
import com.fuchuang.A33.service.Impl.Security.EmployeeDetailsImpl;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    @Autowired
    private AuthenticationManager authenticationManager ;

    @Override
    public Result login(String email) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", email));
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(email,"");
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if (Objects.isNull(employee)){
            throw new RuntimeException("the email is not belong employee") ;
        }
        String token = UUID.randomUUID().toString();
        LoginEmployee loginEmployee = (LoginEmployee) authenticate.getPrincipal();
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtil.copyProperties(loginEmployee,employeeDTO);
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
        stringRedisTemplate.opsForHash().putAll(token,map);
        stringRedisTemplate.expire(token,60*30, TimeUnit.MINUTES) ;
        return Result.success(200,token);
    }

    @Override
    public Result regist(String name, String email, String position, String shop_id) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", email));
        if(!Objects.isNull(employee)){
            throw new RuntimeException("this email has already registed ");
        }
        Long count = employeeMapper.selectCount(new QueryWrapper<>()) + 1;
        String ID = null ;
        if(count<10) ID = "0" + count ;
        else ID = count.toString() ;
        int rows = employeeMapper.insert(new Employee(ID, name, email, position, shop_id, new ArrayList<>()));
        if (rows==0){
            throw new RuntimeException("the system has some problems now") ;
        }
        return Result.success(200) ;
    }
}
