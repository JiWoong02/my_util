package org.mdback.util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    //ex) yyyyMMdd   => return ) 20250319
    public static String getCurrentDate(String format) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return today.format(formatter);
    }
    //ex) (2025-03-19, 15:09) => LocalDateTime 객체로 변환
    public static LocalDateTime convertToOffsetDateTime(String strYmd, String strTime) {
        // 날짜와 시간 결합
        String dateTimeString = strYmd + " " + strTime;
        // 날짜-시간 파싱
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
        // UTC로 변환하여 OffsetDateTime 반환
        return localDateTime;
    }
    //ex) 2025-03-19 => 20250319
    public static String exceptHype(String dateString){
        if(dateString == null){
            return null;
        }
        return dateString.replace("-", "");
    }
    //ex) 20250319 => 2025-03-19
    public static String addHype(String dateString){
        if(dateString == null){
            return null;
        }
        return dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-" + dateString.substring(6);
    }

}
