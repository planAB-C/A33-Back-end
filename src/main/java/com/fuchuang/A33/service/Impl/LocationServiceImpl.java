package com.fuchuang.A33.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fuchuang.A33.DTO.EmployeeDTO;
import com.fuchuang.A33.DTO.EmployeeDetailsInformationDTO;
import com.fuchuang.A33.DTO.WeeksDTO;
import com.fuchuang.A33.DTO.WorkingDTO;
import com.fuchuang.A33.entity.*;
import com.fuchuang.A33.mapper.*;
import com.fuchuang.A33.service.ILocationService;
import com.fuchuang.A33.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.fuchuang.A33.utils.Constants.GET_TOKEN;

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

    @Autowired
    private TimesMapper timesMapper ;

    @Autowired
    private StringRedisTemplate stringRedisTemplate ;

    /**
     * 返回以本周为基准的前后各一个周
     * @param dateTimeWeek
     * @return
     */
    @Override
    public Result getThreeWeeks(String dateTimeWeek) {
        ArrayList<WeeksDTO> weeksDTOList = new ArrayList<>();

        LocalDateTime localDateTime = UsualMethodUtils.StringToChineseLocalDateTime(dateTimeWeek);
        LocalDateTime thisWeek = UsualMethodUtils.parseToMonday(localDateTime);
        LocalDateTime lastWeek = thisWeek.minusWeeks(1) ;
        LocalDateTime nextWeek = thisWeek.plusWeeks(1);

        String lastWeek1 = lastWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String thisWeek1 = thisWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String nextWeek1 = nextWeek.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        WeeksDTO week1 = new WeeksDTO("上周",
                lastWeek1, String.valueOf(lastWeek.getDayOfMonth()),
                String.valueOf(thisWeek.minusDays(1).getDayOfMonth()));

        WeeksDTO week2 = new WeeksDTO("本周",
                thisWeek1, String.valueOf(thisWeek.getDayOfMonth()),
                String.valueOf(nextWeek.minusDays(1).getDayOfMonth()));


        WeeksDTO week3 = new WeeksDTO("下周",
                nextWeek1, String.valueOf(nextWeek.getDayOfMonth()),
                String.valueOf(nextWeek.plusWeeks(1).minusDays(1).getDayOfMonth()));

        weeksDTOList.add(week1) ;
        weeksDTOList.add(week2) ;
        weeksDTOList.add(week3) ;
        return Result.success(200,weeksDTOList);
    }

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
        LocalDateTime localDateTime = UsualMethodUtils.StringToChineseLocalDateTime(dateTimeWeek) ;

        //判断是否是星期一
        if (localDateTime.getDayOfWeek()!=DayOfWeek.MONDAY){
            localDateTime = UsualMethodUtils.parseToMonday(localDateTime) ;
        }
        ArrayList<WorkingDTO> workingDTOList = new ArrayList<>();
        ArrayList<List<WorkingDTO>> list = new ArrayList<>();
        //对本周的值班情况进行查询
        for(int i = 0 ; i < 7 ; i++){
            //将天数进行转化
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
            list.add(workingDTOList) ;
            workingDTOList = new ArrayList<>();
            //天数加1
            localDateTime = localDateTime.plusDays(1) ;
        }
        return Result.success(200, list);
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

    //TODO 按照岗位分组
    /**
     * 小组长按照小组的方式进行对本组员工进行展示
     * @param groupID
     * @return
     */
    @Override
    public Result showAllLocationsByGroup(String groupID) {
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
    public ResultWithToken showEmployeeDetails(String employeeID) {
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
        if (Objects.isNull(employee)){
            return ResultWithToken.fail(500,"the employee is not excite now , please sure it now") ;
        }
        if (employee.getPosition().equals("root")) return ResultWithToken.fail(500,"can not search user named 'root' ") ;
        //对需要返回的具体员工信息封装到EmployeeDetails类中
        EmployeeDetailsInformationDTO employeeDetailsInformationDTO = new EmployeeDetailsInformationDTO();
        employeeDetailsInformationDTO.setEmail(employee.getEmail());
        Employee group = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employee.getID()));

        if (Objects.isNull(group))  employeeDetailsInformationDTO.setGroupName("无");
        else employeeDetailsInformationDTO.setGroupName(group.getName());

        employeeDetailsInformationDTO.setPosition(employee.getPosition());
        Shop shop = shopMapper.selectOne(new QueryWrapper<Shop>().eq("ID", employee.getShopID()));

        if (Objects.isNull(shop)) employeeDetailsInformationDTO.setShopName("无") ;
        else employeeDetailsInformationDTO.setShopName(shop.getName());

        employeeDetailsInformationDTO.setID(employee.getID());
        employeeDetailsInformationDTO.setName(employee.getName());
        employeeDetailsInformationDTO.setPhone(employee.getPhone());
        //添加员工喜好
        List<EmployeeRole> employeeRoleList = employeeRoleMapper.selectList
                (new QueryWrapper<EmployeeRole>().eq("employee_ID", employeeID));
            //将员工喜好依次加入，对于其中为null的部分，我们手动赋值为空，并在数据表中进行修改
        for (EmployeeRole employeeRole : employeeRoleList) {
            switch (employeeRole.getHobbyType()){
                case "工作日偏好" : {
                    if (employeeRole.getHobbyValue().equals("")){
                        employeeDetailsInformationDTO.setHobbyValue1("无");
                        EmployeeRole role = new EmployeeRole();
                        role.setHobbyValue("无");
                        employeeRoleMapper.update(role,new UpdateWrapper<EmployeeRole>()
                                .eq("hobby_type","工作日偏好")
                                .eq("employee_ID",employeeID)) ;
                        break;
                    }
                    employeeDetailsInformationDTO.setHobbyValue1(employeeRole.getHobbyValue());
                    break;
                }

                case "工作时间偏好" : {
                    if (employeeRole.getHobbyValue().equals("")){
                        employeeDetailsInformationDTO.setHobbyValue2("无");
                        EmployeeRole role = new EmployeeRole();
                        role.setHobbyValue("无");
                        employeeRoleMapper.update(role,new UpdateWrapper<EmployeeRole>()
                                .eq("hobby_type","工作时间偏好")
                                .eq("employee_ID",employeeID)) ;
                        break;
                    }
                    employeeDetailsInformationDTO.setHobbyValue2(employeeRole.getHobbyValue());
                    break;
                }

                case "班次时长偏好" : {
                    if (employeeRole.getHobbyValue().equals("")){
                        employeeDetailsInformationDTO.setHobbyValue3("无");
                        EmployeeRole role = new EmployeeRole();
                        role.setHobbyValue("无");
                        employeeRoleMapper.update(role,new UpdateWrapper<EmployeeRole>()
                                .eq("hobby_type","班次时长偏好")
                                .eq("employee_ID",employeeID)) ;
                        break;
                    }
                    employeeDetailsInformationDTO.setHobbyValue3(employeeRole.getHobbyValue());
                    break;
                }
            }
        }
        String token = stringRedisTemplate.opsForValue().get(GET_TOKEN + employee.getID());
        return ResultWithToken.success(200, employeeDetailsInformationDTO,token);
    }

    /**
     * 手动安排员工班次
     * @param locationIDList
     * @param employeeID
     * @return
     */
    @Override
    public Result manageEmployeeLocationsByHand( String employeeID , String... locationIDList)
    {
        ArrayList<Working> workingList = new ArrayList<>();
        for (String locationID : locationIDList) {

            if (locationID.length()!=13)
                return Result.fail(500,"the input is not right") ;
            //根据员工ID号进行查询，将得到的内容填充到working中
            Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("ID", employeeID));
            if (Objects.isNull(employee)) return Result.fail(500,"employee is not excite");
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
            if (!Objects.isNull(work))
                throw new RuntimeException("this employee has exited here now") ;

            //根据用户选中的班次ID进行查询
            Location location = locationsMapper.selectOne(new QueryWrapper<Location>().eq("ID", locationID));
            Location newLocation = new Location();
            //如果查询结果为空，说明该班次没有人，则将current_num赋值为1；否则，在current_num的基础上加1
            if (Objects.isNull(location)) {
                newLocation.setID(locationID);
                newLocation.setCurrentNumber(1);
                String flowID = UsualMethodUtils.getRealFlowID(locationID);
                newLocation.setFlowID(flowID);
                int rows =  locationsMapper.insert(newLocation) ;
                if (rows!=1)  return Result.fail(500,"please try it again") ;
            }else{
                newLocation.setID(locationID);
                newLocation.setCurrentNumber(location.getCurrentNumber()+1);
                newLocation.setFlowID(UsualMethodUtils.getRealFlowID(locationID));
                int rows = locationsMapper.update(newLocation,new UpdateWrapper<Location>().eq("ID",locationID)) ;
            }

            //存入
            workingList.add(working) ;
        }
        int rows = 0 ;
        for (Working working : workingList) {
            rows = workingMapper.insert(working) ;
        }
        int size = workingList.size();
        if (rows!=1)  return Result.fail(500,"please try it again") ;
        //timesmapper
//        rows = timesMapper.update(new Times(), new UpdateWrapper<Times>()
//                .eq("employee_ID", employeeID)
//                .setSql("counts = counts + " + locationIDList.length)
//                .setSql("time_sum = time_sum + " + size * 30));
        Times times = timesMapper.selectOne(new QueryWrapper<Times>().eq("employee_ID",employeeID));
        int counts = times.getCounts() + 1 ;
        double timeSum = counts * 30 ;
        Times time = new Times();
        time.setCounts(counts);
        time.setTimeSum(timeSum);
        rows = timesMapper.update(time, new QueryWrapper<Times>().eq("employee_ID", employeeID));
        if (rows!=1)  return Result.fail(500,"please try it again") ;
        return Result.success(200);
    }

    //location的表只能增加不能删除
    /**
     * 手动移除班次
     * @param locationIDList
     * @param employeeID
     * @return
     */
    @Override
    public Result removeLocationsByHand (String employeeID ,String... locationIDList) {
        String IDs = "" ;
        for (int i = 0 ; i < locationIDList.length ; i++) {
             if(locationIDList.length-1 != i) IDs =  IDs + "\'"  + locationIDList[i] + '\'' + "," ;
             else IDs = IDs + '\'' + locationIDList[i] + '\'' ;
        }
//        ArrayList<Object> IDs = new ArrayList<>(Arrays.asList(locationIDList));
//        List<Working> workings = workingMapper.selectList(new QueryWrapper<Working>().inSql("employee_ID", IDs));
//        int counts = workings.size() ;
        Long counts = workingMapper.selectCount(new QueryWrapper<Working>().inSql("location_ID", IDs));

        if (counts != locationIDList.length)
            return Result.fail(500,"some locations has not excite , please flush the page again") ;

        int rows = workingMapper.delete(new QueryWrapper<Working>()
                .inSql("location_ID", IDs)
                .eq("employee_ID",employeeID));
        if (rows==0){
            return Result.fail(500,"the location is not excite now , please donnot delete agein " );
        }

        rows = locationsMapper.update(new Location(),new UpdateWrapper<Location>()
                        .inSql("ID",IDs)
                .setSql("current_number = current_number - 1"));
        if (rows==0){
            return Result.fail(500,"the location is not excite now , please donnot delete agein " );
        }


        Times times = timesMapper.selectOne(new QueryWrapper<Times>().eq("employee_ID",employeeID));
        int count = times.getCounts() - locationIDList.length ;
        double timeSum = count * 30 ;
        Times time = new Times();
        time.setCounts(count);
        time.setTimeSum(timeSum);
        rows = timesMapper.update(time, new QueryWrapper<Times>().eq("employee_ID", employeeID));
        if (rows==0){
            return Result.fail(500,"the system has some wrongs") ;
        }
        return Result.success(200);
    }

    /**
     * 通过姓名展示用户
     * @param name
     * @return
     */
    @Override
    public Result showEmployeeByName(String name) {
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>().eq("name", name));
        return Result.success(200,employeeList);
    }

    /**
     * 通过email展示位次信息，与前面的showEmployeeByName搭配使用
     * @param email
     * @return
     */
    @Override
    public Result showEmployeeLocationsByEmail(String dateTime , String email) {
        LocalDateTime localDateTime = UsualMethodUtils.StringToChineseLocalDateTime(dateTime) ;
        Employee employee = employeeMapper.selectOne(new QueryWrapper<Employee>().eq("email", email));
        //判断是否是星期一
        if (localDateTime.getDayOfWeek()!=DayOfWeek.MONDAY){
            localDateTime = UsualMethodUtils.parseToMonday(localDateTime) ;
        }
        ArrayList<WorkingDTO> workingDTOList = new ArrayList<>();
        ArrayList<List<WorkingDTO>> list = new ArrayList<>();
        //对本周的值班情况进行查询
        for(int i = 0 ; i < 7 ; i++){
            //将天数进行转化
            String date = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<Location> locationList = locationsMapper.selectList(new QueryWrapper<Location>().like("ID",date));
            for (Location location : locationList) {
                List<Working> workingList = workingMapper.selectList(
                        new QueryWrapper<Working>()
                                .eq("location_ID", location.getID())
                                .eq("employee_ID", employee.getID()));
                for (Working working : workingList) {
                    WorkingDTO workingDTO = new WorkingDTO();
                    //将结果转换成locationDTO对象
                    BeanUtil.copyProperties(working, workingDTO);
                    workingDTO.setLocationRealID(location.getID().substring(11));
                    workingDTOList.add(workingDTO) ;
                }
            }
            list.add(workingDTOList) ;
            workingDTOList = new ArrayList<>();
            //天数加1
            localDateTime = localDateTime.plusDays(1) ;
        }
        return Result.success(200,list);
    }

    /**
     * 当员工的工作时长没有到达要求的时候就会进行展示（root用户不能访问）
     * @return
     */
    @Override
    public Result showFreeEmployees() {
        EmployeeDTO em = EmployeeHolder.getEmloyee();
        String employeeID = em.getID();
        List<Employee> employeeList = employeeMapper.selectList(new QueryWrapper<Employee>()
                .eq("shop_ID", em.getShopID()));
        for (Employee employee : employeeList) {
            Times times = timesMapper.selectOne(new QueryWrapper<Times>().eq("employee_ID", employeeID));
            if ( times.getPermitTime() - times.getTimeSum() >= Constants.MIN_WORKINGTIME ){
                employeeList.remove(employee) ;
            }
        }
        return Result.success(200,employeeList);
    }

}