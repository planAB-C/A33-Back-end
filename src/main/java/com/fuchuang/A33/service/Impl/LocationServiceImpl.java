package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.DTO.EmployeeDetailsDTO;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.Locations;
import com.fuchuang.A33.entity.Shop;
import com.fuchuang.A33.entity.Working;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.mapper.LocationsMapper;
import com.fuchuang.A33.mapper.ShopMapper;
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

    @Autowired
    private ShopMapper shopMapper ;

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

    /**
     * 展示员工具体细节信息
     * @param employeeID
     * @return
     */
    @Override
    public Result showEmployeeDeatils(String employeeID) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        if (employee==null){
            throw new RuntimeException("the employee is not excite now , please sure it now") ;
        }
        //对需要返回的具体员工信息封装到EmployeeDetails类中
        EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
        employeeDetailsDTO.setEmail(employee.getEmail());
        Employee group = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employee.getID()));
        if (Objects.isNull(group))  employeeDetailsDTO.setGroupName("无");
        else employeeDetailsDTO.setGroupName(group.getName());
        employeeDetailsDTO.setPosition(employee.getPosition());
        Shop shop = shopMapper.selectOne(new QueryWrapper<Shop>().eq("ID", employee.getShopID()));
        if (Objects.isNull(shop)) throw new RuntimeException("System has some wrongs now") ;
        else employeeDetailsDTO.setShopName(shop.getName());
        employeeDetailsDTO.setID(employee.getID());
        employeeDetailsDTO.setName(employee.getName());
        return Result.success(200,employeeDetailsDTO);
    }

    /**
     * 手动安排员工班次
     * @param locationID
     * @param employeeID
     * @return
     */
    @Override
    public Result manageEmployeeLocationsByHand( String locationID, String employeeID) {
        //根据员工ID号进行查询，将得到的内容填充到working中
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        Working working = new Working();
        working.setName(employee.getName());
        working.setPosition(employee.getPosition());
        working.setLocationID(locationID);
        working.setEmployeeID(employeeID);
        working.setShopID(employee.getShopID());

        //根据用户选中的班次ID进行查询
        QueryWrapper<Locations> locationsQueryWrapper = new QueryWrapper<>();
        locationsQueryWrapper.eq("ID",locationID ) ;
        Locations location = locationsMapper.selectOne(locationsQueryWrapper);
        Locations newLocation = new Locations();
        //如果查询结果为空，说明该班次没有人，则将current_num赋值为1；否则，在current_num的基础上加1
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
        //插入
        int rows = workingMapper.insert(working) ;
        rows = rows + locationsMapper.insert(newLocation) ;
        return Result.success(200);
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
