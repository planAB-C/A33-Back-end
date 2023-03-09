package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.EmployeeServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Api(tags = "员工接口")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService ;

    @GetMapping("/login")
    @ApiOperation(value = "登陆（返回值是token，前端需要将token设置为全局变量，每次访问时请求头都需要携带token）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "员工邮箱" ,dataType= "String")
    })
    public Result login(String email){
        return employeeService.login(email) ;
    }

    @PostMapping("/regist")
    @ApiOperation(value = "注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "员工姓名" ,dataType= "String") ,
            @ApiImplicitParam(name = "email", value = "员工邮箱" ,dataType= "String") ,
            @ApiImplicitParam(name = "position", value = "员工职位" ,dataType= "String") ,
            @ApiImplicitParam(name = "shop_ID", value = "员工所属商店的ID号" ,dataType= "String")
    })
    public Result regist(String name , String email ,String position ,String shop_ID){
        return employeeService.regist(name ,email ,position ,shop_ID) ;
    }


}
