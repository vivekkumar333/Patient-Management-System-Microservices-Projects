package com.vkt.pms.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomUtility {
    public static LocalDate stringToLocalDateConvertor(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
