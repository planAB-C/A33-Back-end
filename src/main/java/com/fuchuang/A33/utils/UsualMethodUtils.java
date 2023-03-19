package com.fuchuang.A33.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.DTO.WorkingDTO;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.Working;
import com.fuchuang.A33.mapper.WorkingMapper;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsualMethodUtils {

    public static LocalDateTime StringToChineseLocalDateTime(String date){
        return LocalDateTime.of(Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)), 0, 0)
                .atZone(ZoneId.of("Asia/Shanghai")).withZoneSameInstant(ZoneId.of("Asia/Shanghai")).toLocalDateTime();
    }

    public static LocalDateTime parseToMonday(String date){
        LocalDateTime localDateTime = StringToChineseLocalDateTime(date);
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        while(dayOfWeek!=DayOfWeek.MONDAY){
            localDateTime = localDateTime.minusDays(1) ;
            dayOfWeek = localDateTime.getDayOfWeek() ;
        }
        return localDateTime ;
    }

    public static LocalDateTime parseToMonday(LocalDateTime localDateTime){
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        while(dayOfWeek!=DayOfWeek.MONDAY){
            localDateTime = localDateTime.minusDays(1) ;
            dayOfWeek = localDateTime.getDayOfWeek() ;
        }
        return localDateTime ;
    }

    public static String parseID(String ID){
        if (ID.contains("\"")){
            String[] split = ID.split("\"");
            return  split[1];
        }
        return ID ;
    }
    public static String getRealFlowID(String locationID ){
        String ID ;
        LocalDateTime week = StringToChineseLocalDateTime(locationID.substring(0, 10));
        int i  ;
        for( i = 0 ; i < 7 ; i++ ){
            if (week.minusDays(i).getDayOfWeek() == DayOfWeek.MONDAY) break ;
        }
        i = i * Constants.ONEDAY_COUNTS + Integer.parseInt(locationID.substring(11,13)) - 1 ;
        if( i < 10 ) ID = "0" + i ;
        else ID = String.valueOf(i) ;
        return ID ;
    }

    public static ArrayList<ArrayList<ArrayList<WorkingDTO>>> getLocationsByWorking(List<Employee> employeeList
            , WorkingMapper workingMapper , String dateTime){
        ArrayList<WorkingDTO> workingDTOList1 = new ArrayList<>();
        ArrayList<ArrayList<WorkingDTO>> workingDTOList2 = new ArrayList<>();
        ArrayList<ArrayList<ArrayList<WorkingDTO>>> workingDTOList3 = new ArrayList<>();

        LocalDateTime localDateTime = parseToMonday(dateTime);
        LocalDateTime time = localDateTime.plusWeeks(1);
        while(localDateTime.isBefore(time)){
            for (Employee employee : employeeList) {
                List<Working> workingList = workingMapper.selectList(new QueryWrapper<Working>()
                        .eq("employee_ID", employee.getID())
                        .likeRight("location_ID",localDateTime.toString().substring(0,10)));
                if(workingList.size()!=0 && employeeList.size()!=0) {
                    for (Working working : workingList) {
                        if (!Objects.isNull(working)) {
                            WorkingDTO workingDTO = new WorkingDTO();
                            BeanUtil.copyProperties(working, workingDTO);
                            workingDTO.setLocationRealID(working.getLocationID().substring(11));
                            workingDTOList1.add(workingDTO);
                            workingDTOList2.add(workingDTOList1) ;
                            workingDTOList1 = new ArrayList<>() ;
                        }
                    }
                }
            }
            if (workingDTOList2.isEmpty()){
                workingDTOList2.add(new ArrayList<>()) ;
            }
            workingDTOList3.add(workingDTOList2) ;
            workingDTOList2 = new ArrayList<>() ;
            localDateTime = localDateTime.plusDays(1) ;
        }
        return workingDTOList3 ;
    }

}
