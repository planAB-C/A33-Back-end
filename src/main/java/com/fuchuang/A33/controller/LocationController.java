package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/location")
@Api(tags = "员工班次")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService ;

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/week")
    public Result showAllLocationsByWeek(LocalDateTime dateTimeWeek){
        return Result.success(200) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/day")
    public Result showAllLocationsByDay(LocalDateTime dateTimeDay){
        return Result.success(200) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/group")
    public Result showAllLocationsByGroup(String group){
        return Result.success(200) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @ApiOperation(value = "展示员工信息的具体细节")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String") ,
    })
    @GetMapping("/details")
    public Result showEmployeeDetails(String employeeID){
        return locationService.showEmployeeDetails(employeeID) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage')")
    @PutMapping("/manage")
    @ApiOperation(value = "手动安排空闲的员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationID", value = "值班号" ,dataType= "String") ,
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String")
    })
    public Result manageEmployeeLocationsByHand( String locationID , String employeeID){
        return locationService.manageEmployeeLocationsByHand( locationID, employeeID) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage')")
    @DeleteMapping("/remove")
    public Result removeLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/name")
    public Result selectLocationByName(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/freeEmployees")
    public Result showFreeEmployees(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

}
