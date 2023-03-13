package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.LocationServiceImpl;
import com.fuchuang.A33.utils.Result;
import com.fuchuang.A33.utils.ResultWithToken;
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
    //TODO 分清楚哪些功能root用户无法访问
    @Autowired
    private LocationServiceImpl locationService ;

    @GetMapping("/monday")
    @ApiOperation(value = "获取本周星期一的日期")
    public Result getMondayThisWeek(){
        return locationService.getMondayThisWeek() ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/week")
    @ApiOperation(value = "按周展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTimeWeek", value = "选中的周" ,dataType= "String")
    })
    public Result showAllLocationsByWeek(String dateTimeWeek){
        return locationService.showAllLocationsByWeek(dateTimeWeek) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/day")
    @ApiOperation(value = "按日展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTimeDay", value = "选中的日期" ,dataType= "String") ,
    })
    public Result showAllLocationsByDay(String dateTimeDay){
        return locationService.showAllLocationsByDay(dateTimeDay) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage')")
    @GetMapping("/allGroup")
    @ApiOperation(value = "展示所有的小组")
    public Result showAllGroup(){
        return locationService.showAllGroup() ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/group")
    @ApiOperation(value = "按组展示员工的工作安排")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupID", value = "选中的组别，将选中的组的ID信息传入" ,dataType= "Integer") ,
    })
    public Result showAllLocationsByGroup(Integer groupID){
        return locationService.showAllLocationsByGroup(groupID) ;
    }

    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/details")
    @ApiOperation(value = "展示员工信息的具体细节")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String") ,
    })
    public ResultWithToken showEmployeeDetails(String employeeID){
        return locationService.showEmployeeDetails(employeeID) ;
    }

    //TODO
    @PreAuthorize("hasAnyAuthority('root','boss','manage')")
    @PutMapping("/manage")
    @ApiOperation(value = "手动安排员工班次")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationID", value = "值班号" ,dataType= "String") ,
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String")
    })
    public Result manageEmployeeLocationsByHand( String locationID , String employeeID){
        return locationService.manageEmployeeLocationsByHand( locationID, employeeID) ;
    }

    //TODO
    @PreAuthorize("hasAnyAuthority('root','boss','manage')")
    @DeleteMapping("/remove")
    @ApiOperation(value = "手动移除员工班次")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "locationID", value = "值班号" ,dataType= "String") ,
            @ApiImplicitParam(name = "employeeID", value = "员工ID号" ,dataType= "String")
    })
    public Result removeLocationsByHand( String LocationID , String employeeID){
        return locationService.removeLocationsByHand( LocationID , employeeID) ;
    }

    //TODO
    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/name")
    @ApiOperation(value = "（搜索时使用）通过员工姓名查询员工姓名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "姓名" ,dataType= "String")
    })
    public Result showEmployeeByName( String name){
        return locationService.showEmployeeByName( name) ;
    }

    //TODO
    @PreAuthorize("hasAnyAuthority('root','boss','manage','group')")
    @GetMapping("/freeEmployees")
    @ApiOperation(value = "搭配/A33/location/name接口使用，用于展示所搜索的用户所有的排班信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dateTime", value = "日期" ,dataType= "String") ,
            @ApiImplicitParam(name = "name", value = "员工姓名" ,dataType= "String")
    })
    public Result showEmployeeLocationsByEmail(String dateTime ,String email){
        return locationService.showEmployeeLocationsByEmail(dateTime , email) ;
    }

    //TODO
    @PreAuthorize("hasAnyAuthority('boss','manage','group')")
    @GetMapping("/showFree")
    @ApiOperation(value = "展示所有的空闲用户")
    public Result showFreeEmployees(){
        return locationService.showFreeEmployees() ;
    }

    //TODO
}
