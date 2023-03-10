package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ILocationService {
    //TODO 梁贤卓
    Result showAllLocationsByWeek(LocalDateTime dateTimeWeek);
    Result showAllLocationsByDay(LocalDateTime dateTimeDay);
    //分组展示，个人理解为按照职位展示
    Result showAllLocationsByGroup(String group);
    Result showEmployeeDetails(String employeeID) ;
    Result manageEmployeeLocationsByHand( String LocationID , String employeeID) ;
    //TODO 张子豪
    Result removeLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result selectLocationByName(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result showFreeEmployees(LocalDateTime dateTimeWeek) ;
}
