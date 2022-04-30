package com.example.lab3_behind.utils;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.domain.dto.YearSemesterPair;

import java.util.Calendar;

public class TimeTool {
    public static YearSemesterPair getPresentYearAndSemester(){
        YearSemesterPair result = new YearSemesterPair();
        Calendar cal=Calendar.getInstance();
        if((cal.get(Calendar.MONTH)*100 + cal.get(Calendar.DATE)) >
                (Global.FIRST_SEMESTER_START_TIME.getFirst()*100 + Global.SECOND_SEMESTER_START_TIME.getSecond())){
            result.setYear(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.YEAR) + 1));
            result.setSemester("第1学期");
        } else{
            result.setYear((cal.get(Calendar.YEAR) - 1) + "-" + (cal.get(Calendar.YEAR)));
            if((cal.get(Calendar.MONTH)*100 + cal.get(Calendar.DATE)) <
                    (Global.FIRST_SEMESTER_START_TIME.getFirst()*100 + Global.SECOND_SEMESTER_START_TIME.getSecond())) {
                result.setSemester("第2学期");
            } else {
              result.setSemester("第1学期");
            }
        }
        return result;
    }
}
