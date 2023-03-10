package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.Locations;
import com.fuchuang.A33.entity.Working;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.mapper.LocationsMapper;
import com.fuchuang.A33.mapper.WorkingMapper;
import com.fuchuang.A33.service.ILocationService;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Service
public class LocationServiceImpl implements ILocationService {

    @Autowired
    private LocationsMapper locationsMapper ;

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private WorkingMapper workingMapper ;

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
    public Result manageFreeEmployeeLocationsByHand( String locationID, String employeeID) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        Working working = new Working();
        working.setName(employee.getName());
        working.setPosition(employee.getPosition());
        working.setLocationID(locationID);
        working.setEmployeeID(employeeID);
        working.setShopID(employee.getShopID());

        QueryWrapper<Locations> locationsQueryWrapper = new QueryWrapper<>();
        locationsQueryWrapper.eq("ID",locationID ) ;
        Locations location = locationsMapper.selectOne(locationsQueryWrapper);
        Locations newLocation = new Locations();
        if (Objects.isNull(location)) {
            newLocation.setID(locationID);
            newLocation.setCurrentNumber(1);
            String flowID = locationID.substring(10);
            newLocation.setFlowID(flowID);
        }else{
            newLocation.setID(locationID);
            newLocation.setCurrentNumber(location.getCurrentNumber()+1);
            String flowID = locationID.substring(10);
            newLocation.setFlowID(flowID);
        }
        workingMapper.insert(working) ;
        locationsMapper.insert(newLocation) ;

        return Result.success(200);
    }

    @Override
    public Result manageOtherEmployeeLocationsByHand( String locationID, String employeeID) {
        return null;
    }

    @Override
    public Result removeLocationsByHand(LocalDateTime dateTimeWeek, String locationID, String employeeID) {
        return null;
    }

    @Override
    public Result selectLocationByName(LocalDateTime dateTimeWeek, String locationID, String employeeID) {
        return null;
    }

    @Override
    public Result showFreeEmployees(LocalDateTime dateTimeWeek) {
        return null;
    }

}
