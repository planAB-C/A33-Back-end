package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/location")
@Api(tags = "员工班次")
public class LocationController {

    @Autowired
    private LocationServiceImpl locationService ;

    @PreAuthorize("hasAnyAuthority('root','manage','group','view')")
    @GetMapping("/monday")
    @ApiOperation(value = "获取本周星期一的日期")
    public Result getMondayThisWeek(){
        return locationService.getMondayThisWeek() ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/week")
    @ApiOperation(value = "按周展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTimeWeek", value = "选中的周" ,dataType= "String")
    })
    public Result showAllLocationsByWeek(String dateTimeWeek){
        return locationService.showAllLocationsByWeek(dateTimeWeek) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/day")
    @ApiOperation(value = "按日展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTimeDay", value = "选中的日期" ,dataType= "String") ,
    })
    public Result showAllLocationsByDay(String dateTimeDay){
        return locationService.showAllLocationsByDay(dateTimeDay) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage')")
    @GetMapping("/allGroup")
    @ApiOperation(value = "展示所有的小组")
    public Result showAllGroup(){
        return locationService.showAllGroup() ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/group")
    @ApiOperation(value = "按组展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupID", value = "选中的组别，将选中的组的ID信息传入" ,dataType= "Integer") ,
    })
    public Result showAllLocationsByGroup(Integer groupID){
        return locationService.showAllLocationsByGroup(groupID) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group')")
    @GetMapping("/details")
    @ApiOperation(value = "展示员工信息的具体细节")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String") ,
    })
    public Result showEmployeeDetails(String employeeID){
        return locationService.showEmployeeDetails(employeeID) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage')")
    @PutMapping("/manage")
    @ApiOperation(value = "手动安排员工")
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
