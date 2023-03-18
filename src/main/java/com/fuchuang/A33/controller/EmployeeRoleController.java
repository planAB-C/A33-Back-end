package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.EmployeeRoleServiceImpl;
import com.fuchuang.A33.utils.UsualMethodUtils;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employeeRole")
@Api(tags = "员工偏好")
public class EmployeeRoleController {
    @Autowired
    private EmployeeRoleServiceImpl employeeRoleService ;

    @PreAuthorize("hasAnyAuthority('boss','manage','group','view')")
    @PostMapping("/add")
    @ApiOperation(value = "更改员工偏好")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID" ,dataType= "String"),

            @ApiImplicitParam(name = "hobbyType1", value = "员工偏好1" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyValue1", value = "员工偏好值1" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyType2", value = "员工偏好2" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyValue2", value = "员工偏好值2" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyType3", value = "员工偏好3" ,dataType= "String"),
            @ApiImplicitParam(name = "hobbyValue3", value = "员工偏好值3" ,dataType= "String")
    })
    public Result UpdateEmployeeRole(String employeeID, String hobbyType1, String hobbyValue1 ,String hobbyType2, String hobbyValue2 ,
                                          String hobbyType3, String hobbyValue3){
        return employeeRoleService.UpdateEmployeeRole(UsualMethodUtils.parseID(employeeID), hobbyType1, hobbyValue1 , hobbyType2,hobbyValue2 ,
                 hobbyType3,  hobbyValue3) ;
    }

}
