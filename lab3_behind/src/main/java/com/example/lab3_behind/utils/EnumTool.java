package com.example.lab3_behind.utils;

import com.example.lab3_behind.common.forDomain.CourseSelectType;
import com.example.lab3_behind.common.forDomain.Grade;

public class EnumTool {
    public static String transString(CourseSelectType courseSelectType){
        if(courseSelectType==null) return null;
        switch (courseSelectType){
            case common: return "通识课程";
            case major:return "专业限制课程";
            default:return "--";
        }
    }

    public static String transString(Grade grade){
        if(grade==null) return null;
        switch (grade){
            case G2019:return "2019级";
            case G2020:return "2020级";
            case G2021:return "2021级";
            case G2022:return "2022级";
            default:return "--";
        }
    }
}
