package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/location")
@Api(tags = "员工班次")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService ;

    @GetMapping("/week")
    public Result showAllLocationsByWeek(LocalDateTime dateTimeWeek){
        return Result.success(200) ;
    }

    @GetMapping("/day")
    public Result showAllLocationsByDay(LocalDateTime dateTimeDay){
        return Result.success(200) ;
    }

    @GetMapping("/group")
    public Result showAllLocationsByGroup(String group){
        return Result.success(200) ;
    }

    @GetMapping("/details")
    public Result showEmployeeDeatils(String ID){
        return Result.success(200) ;
    }

    @PutMapping("/manage")
    @ApiOperation(value = "手动安排空闲的员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationID", value = "值班号" ,dataType= "String") ,
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String")
    })
    public Result manageFreeEmployeeLocationsByHand( String locationID , String employeeID){
        return locationService.manageFreeEmployeeLocationsByHand( locationID, employeeID) ;
    }

    @PutMapping("/manageOther")
    public Result manageOtherEmployeeLocationsByHand( String locationID , String employeeID){
        return Result.success(200) ;
    }

    @DeleteMapping("/remove")
    public Result removeLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

    @GetMapping("/name")
    public Result selectLocationByName(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

    @GetMapping("/freeEmployees")
    public Result showFreeEmployees(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
        return Result.success(200) ;
    }

}
