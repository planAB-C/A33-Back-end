package com.fuchuang.A33.service.Impl;

import com.fuchuang.A33.service.ILocationService;
import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationServiceImpl implements ILocationService {
    @Override
    public Result showAllLocationsByWeek(LocalDateTime dateTimeWeek) {
        return null;
    }

    @Override
    public Result showAllLocationsByDay(LocalDateTime dateTimeDay) {
        return null;
    }

    @Override
    public Result showAllLocationsByGroup(String group) {
        return null;
    }

    @Override
    public Result showEmployeeDeatils(String ID) {
        return null;
    }

    @Override
    public Result addLocationsByHand(LocalDateTime dateTimeWeek, String LocationID, String employeeID) {
        return null;
    }

    @Override
    public Result removeLocationsByHand(LocalDateTime dateTimeWeek, String LocationID, String employeeID) {
        return null;
    }

    @Override
    public Result selectLocationByName(LocalDateTime dateTimeWeek, String LocationID, String employeeID) {
        return null;
    }

    @Override
    public Result showFreeEmployees(LocalDateTime dateTimeWeek) {
        return null;
    }

    @Override
    public Result showFreeEmployees(LocalDateTime dateTimeWeek, String LocationID, String employeeID) {
        return null;
    }
}
