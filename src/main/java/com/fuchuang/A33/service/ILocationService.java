package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ILocationService {
    Result getMondayThisWeek() ;
    Result showAllLocationsByWeek(String dateTimeDay);
    Result showAllLocationsByDay(String dateTimeDay);
    //分组展示，个人理解为按照职位展示
    Result showAllGroup () ;
    Result showAllLocationsByGroup(Integer groupID);
    Result showEmployeeDetails(String employeeID) ;
    Result manageEmployeeLocationsByHand( String LocationID , String employeeID) ;
    //TODO 张子豪
    Result removeLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result selectLocationByName(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result showFreeEmployees(LocalDateTime dateTimeWeek) ;
}
