package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.EmployeeRoleServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeeRole")
@Api(tags = "员工偏好")
public class EmployeeRoleController {
    @Autowired
    private EmployeeRoleServiceImpl employeeRoleService ;

    @PreAuthorize("hasAnyAuthority('root','manage','group','view')")
    @PostMapping("/add")
    @ApiOperation(value = "添加员工偏好")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyType", value = "员工偏好" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyValue", value = "员工偏好值" ,dataType= "String")
    })
    public Result addOrUpdateEmployeeRole(String employeeID, String hobbyType, String hobbyValue){
        return employeeRoleService.addOrUpdateEmployeeRole(employeeID,hobbyType,hobbyValue) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group','view')")
    @DeleteMapping("/remove")
    @ApiOperation(value = "移除员工偏好")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyType", value = "员工偏好" ,dataType= "String")
    })
    public Result removeEmployeeRole(String employeeID ,String hobbyType){
        return employeeRoleService.removeEmployeeRole(employeeID ,hobbyType) ;
    }
}
