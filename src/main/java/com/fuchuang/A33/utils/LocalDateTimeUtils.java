package com.fuchuang.A33.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class LocalDateTimeUtils {

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
}
