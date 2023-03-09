package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ILocationService {
    Result showAllLocationsByWeek(LocalDateTime dateTimeWeek);
    Result showAllLocationsByDay(LocalDateTime dateTimeDay);
    Result showAllLocationsByGroup(String group);
    Result showEmployeeDeatils(String ID) ;
    Result addLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result removeLocationsByHand(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result selectLocationByName(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
    Result showFreeEmployees(LocalDateTime dateTimeWeek) ;
    Result showFreeEmployees(LocalDateTime dateTimeWeek , String LocationID , String employeeID) ;
}
