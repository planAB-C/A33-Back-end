package com.fuchuang.A33.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.DTO.EmployeeDetailsInfomationDTO;
import com.fuchuang.A33.DTO.WorkingDTO;
import com.fuchuang.A33.entity.*;
import com.fuchuang.A33.mapper.*;
import com.fuchuang.A33.service.ILocationService;
import com.fuchuang.A33.utils.Result;
import com.fuchuang.A33.utils.UsualUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper ;


    /**
     * 得到本周的星期一的日期
     * @return
     */
    @Override
    public Result getMondayThisWeek() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        while(dayOfWeek != DayOfWeek.MONDAY){
            now = now.minusDays(1);
            dayOfWeek = now.getDayOfWeek() ;
        }
        String Monday = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return Result.success(200,Monday);
    }

    /**
     * 按周进行查看
     * @param dateTimeWeek
     * @return
     */
    @Override
    public Result showAllLocationsByWeek(String dateTimeWeek) {
//        String dateTime = getMonday(dateTimeWeek) ;
        //对字符串进行解析
        LocalDateTime localDateTime =UsualUtils.StringToChineseLocalDateTime(dateTimeWeek) ;

        //判断是否是星期一
        if (localDateTime.getDayOfWeek()!=DayOfWeek.MONDAY){
            localDateTime = UsualUtils.parseToMonday(localDateTime) ;
        }
        ArrayList<WorkingDTO> workingDTOList = new ArrayList<>();

        //对本周的值班情况进行查询
        for(int i = 0 ; i < 7 ; i++){
            String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<Location> locationList = locationsMapper.selectList(new QueryWrapper<Location>().like("ID",date));
            for (Location location : locationList) {
                List<Working> workingList = workingMapper.selectList(new QueryWrapper<Working>().eq("location_ID", location.getID()));
                for (Working working : workingList) {
                    WorkingDTO workingDTO = new WorkingDTO();
                    //将结果转换成locationDTO对象
                    BeanUtil.copyProperties(working, workingDTO);
                    workingDTO.setLocationRealID(location.getID().substring(11));
                    workingDTOList.add(workingDTO) ;
                }
            }

            localDateTime = localDateTime.plusDays(1) ;
        }
        return Result.success(200, workingDTOList);
    }

    /**
     * 按日进行查看
     * @param dateTimeDay
     * @return
     */
    @Override
    public Result showAllLocationsByDay(String dateTimeDay) {
        //同上述用法一致
        List<Location> locationList = locationsMapper.selectList(new QueryWrapper<Location>().like("ID",dateTimeDay));
        ArrayList<WorkingDTO> workingDTOList = new ArrayList<>();
        for (Location location : locationList) {
            String LocationRealID = location.getID().substring(11);
            List<Working> workingList = workingMapper.selectList(new QueryWrapper<Working>()
                    .eq("location_ID", location.getID()));
            for (Working working : workingList) {
                WorkingDTO workingDTO = new WorkingDTO();
                BeanUtil.copyProperties(working, workingDTO);
                workingDTO.setLocationRealID(LocationRealID);
                workingDTOList.add(workingDTO) ;
            }
        }
        return Result.success(200, workingDTOList);
    }

    /**
     * 展示所有的组别信息，我们在这里面只展示小组长的姓名，依据小组长的ID号进行分组
     * @return
     */
    @Override
    public Result showAllGroup() {
        List<Employee> employees = employeeMapper.selectList(new QueryWrapper<Employee>().eq("position", "小组长"));
        return Result.success(200,employees);
    }

    /**
     * 小组长按照小组的方式进行对本组员工进行展示
     * @param groupID
     * @return
     */
    @Override
    public Result showAllLocationsByGroup(Integer groupID) {
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().eq("belong", groupID));
        ArrayList<WorkingDTO> workingDTOS = new ArrayList<>();
        for (Employee employee : employeeList) {
            Working working = workingMapper.selectOne(new QueryWrapper<Working>().eq("ID", employee.getID()));
            WorkingDTO workingDTO = new WorkingDTO();
            BeanUtil.copyProperties(working,workingDTO);
            workingDTO.setLocationRealID(working.getLocationID().substring(11));
            workingDTOS.add(workingDTO) ;
        }
        return Result.success(200,workingDTOS);
    }

    /**
     * 展示员工具体细节信息，通过封装员工信息得到
     * @param employeeID
     * @return
     */
    @Override
    public Result showEmployeeDetails(String employeeID) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        if (employee==null){
            throw new RuntimeException("the employee is not excite now , please sure it now") ;
        }
        //对需要返回的具体员工信息封装到EmployeeDetails类中
        EmployeeDetailsInfomationDTO employeeDetailsInfomationDTO = new EmployeeDetailsInfomationDTO();
        employeeDetailsInfomationDTO.setEmail(employee.getEmail());
        Employee group = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employee.getID()));

        if (Objects.isNull(group))  employeeDetailsInfomationDTO.setGroupName("无");
        else employeeDetailsInfomationDTO.setGroupName(group.getName());

        employeeDetailsInfomationDTO.setPosition(employee.getPosition());
        Shop shop = shopMapper.selectOne(new QueryWrapper<Shop>().eq("ID", employee.getShopID()));

        if (Objects.isNull(shop)) throw new RuntimeException("System has some wrongs now") ;
        else employeeDetailsInfomationDTO.setShopName(shop.getName());

        employeeDetailsInfomationDTO.setID(employee.getID());
        employeeDetailsInfomationDTO.setName(employee.getName());
        //添加员工喜好
        HashMap<String,String> map = new HashMap<>();
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectList
                (new QueryWrapper<EmployeeRole>().eq("employee_ID", employeeID));
        for (EmployeeRole employeeRole : employeeRoleList) {
            map.put(employeeRole.getHobbyType(),employeeRole.getHobbyValue()) ;
        }
        if (map.isEmpty()){
            map.put("null","null") ;
        }
        employeeDetailsInfomationDTO.setEmployeeRole(map);
        return Result.success(200, employeeDetailsInfomationDTO);
    }

    /**
     * 手动安排员工班次
     * @param locationID
     * @param employeeID
     * @return
     */
    @Override
    public Result manageEmployeeLocationsByHand( String locationID, String employeeID) {
        if (locationID.length()!=13) throw new RuntimeException("the input is not right") ;
        //根据员工ID号进行查询，将得到的内容填充到working中
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        Working working = new Working();
        working.setName(employee.getName());
        working.setPosition(employee.getPosition());
        working.setLocationID(locationID);
        working.setEmployeeID(employeeID);
        working.setShopID(employee.getShopID());
        //查看同一个人是否被填入了相同的位置
        Working work = workingMapper.selectOne(new QueryWrapper<Working>()
                .eq("employee_ID", employeeID)
                .eq("location_ID", locationID));
        if (!Objects.isNull(work)) return Result.fail(500,"this employee has exited here now") ;

        //根据用户选中的班次ID进行查询
        QueryWrapper<Location> locationsQueryWrapper = new QueryWrapper<>();
        locationsQueryWrapper.eq("ID",locationID ) ;
        Location location = locationsMapper.selectOne(locationsQueryWrapper);
        Location newLocation = new Location();
        //如果查询结果为空，说明该班次没有人，则将current_num赋值为1；否则，在current_num的基础上加1
        if (Objects.isNull(location)) {
            newLocation.setID(locationID);
            newLocation.setCurrentNumber(1);
            String flowID = locationID.substring(11);
            newLocation.setFlowID(flowID);
        }else{
            newLocation.setID(locationID);
            newLocation.setCurrentNumber(location.getCurrentNumber()+1);
            String flowID = locationID.substring(11);
            newLocation.setFlowID(flowID);
        }
        //插入
        int rows = workingMapper.insert(working) ;
        rows = rows + locationsMapper.insert(newLocation) ;
        if (rows!=2)  return Result.fail(500,"please try it again") ;
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

    public String getMonday(String date) {
        return null;
    }
}
