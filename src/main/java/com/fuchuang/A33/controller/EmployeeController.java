package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.EmployeeServiceImpl;
import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.EmployeeHolder;
import com.fuchuang.A33.utils.UsualMethodUtils;
import com.fuchuang.A33.utils.Result;
import com.fuchuang.A33.utils.ResultWithToken;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Api(tags = "员工接口")
public class EmployeeController {

    @Autowired
    private LocationServiceImpl locationService ;

    @Autowired
    private EmployeeServiceImpl employeeService ;

    @GetMapping("/login")
    @ApiOperation(value = "登陆（返回值是token，前端需要将token设置为全局变量，每次访问时请求头都需要携带token）" ,position = 1)
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
            @ApiImplicitParam(name = "shopID", value = "员工所属商店的ID号" ,dataType= "String") ,
            @ApiImplicitParam(name = "belong", value = "员工所属的组长的邮箱" ,dataType= "String")
    })
    public Result regist(String name , String email ,String position ,String shopID ,String belong){
        return employeeService.regist(name ,email ,position ,shopID ,belong) ;
    }

    @PreAuthorize("hasAuthority('view')")
    @PutMapping("/group")
    @ApiOperation(value = "更改小组长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupEmail", value = "小组长邮箱" ,dataType= "String" ) ,
    })
    public Result regist(String groupEmail ){
        return employeeService.changeGroup(groupEmail) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group','view')")
    @GetMapping("/own")
    @ApiOperation(value = "展示个人信息" )
    public ResultWithToken showOwnImformation(){
        return locationService.showEmployeeDetails(EmployeeHolder.getEmloyee().getID()) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group','view')")
    @PutMapping("/updateOwn")
    @ApiOperation(value = "修改个人信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "员工邮箱" ,dataType= "String") ,
            @ApiImplicitParam(name = "position", value = "员工职位" ,dataType= "String") ,
    })
    public Result updateOwnImformation(String email,String position ){
        return employeeService.updateEmployeeInformation(email,position ) ;
    }

    @PreAuthorize("hasAnyAuthority('boss','manage','group','view')")
    @GetMapping("/other")
    @ApiOperation(value = "(非root用户使用)展示本店铺其他人的信息" )
    public Result showOtherImformation(){
        return employeeService.showOtherImformation() ;
    }

    @PreAuthorize("hasAnyAuthority('root')")
    @GetMapping("/rootForAll")
    @ApiOperation(value = "root展示店铺所有人的信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "店铺ID" ,dataType= "String") ,
    })
    public Result showOtherImformation(String shopID){
        return employeeService.showEmployeeByRoot(UsualMethodUtils.parseID(shopID)) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss')")
    @PutMapping("/updateOther")
    @ApiOperation(value = "高权限用户(root,boss)修改员工信息" )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ID", value = "员工ID，前端选中后传入" ,dataType= "String") ,
            @ApiImplicitParam(name = "email", value = "员工邮箱" ,dataType= "String") ,
            @ApiImplicitParam(name = "position", value = "员工职位" ,dataType= "String") ,
            @ApiImplicitParam(name = "belong", value = "员工所属的组长的邮箱" ,dataType= "String")
    })
    public Result updateOtherImformation(String ID ,String email,String position , String belong){
        return employeeService.updateOtherImformation( UsualMethodUtils.parseID(ID) ,  email , position , belong) ;
    }

    @PreAuthorize("hasAnyAuthority('root')")
    @GetMapping("/allShop")
    @ApiOperation(value = "展示所有店铺（root用户专用页面）" )
    public Result showAllShop(){
        return employeeService.showAllShop() ;
    }

}
