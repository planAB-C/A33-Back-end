package com.fuchuang.A33.controller;

import com.fuchuang.A33.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/location")
public class LocationController {

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
    public Result manageLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID){
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
