package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import com.fuchuang.A33.utils.ResultWithToken;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ILocationService {
    Result getThreeWeeks(String dateTimeWeek) ;
    Result getMondayThisWeek() ;
    Result showAllLocationsByWeek(String dateTimeDay);
    Result showAllLocationsByDay(String dateTimeDay);
    Result showAllGroup () ;
    Result showAllLocationsByGroup(String groupID);
    ResultWithToken showEmployeeDetails(String employeeID) ;
    Result manageEmployeeLocationsByHand( String LocationID , String employeeID) ;
    Result removeLocationsByHand( String LocationID , String employeeID) ;
    Result showEmployeeByName( String name) ;
    Result showEmployeeLocationsByEmail(String dateTime ,String email) ;
    Result showFreeEmployees() ;
}
