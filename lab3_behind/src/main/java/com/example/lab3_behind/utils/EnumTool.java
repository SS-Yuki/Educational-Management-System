package com.example.lab3_behind.utils;

import com.example.lab3_behind.common.forDomain.CourseSelectType;
import com.example.lab3_behind.common.forDomain.Grade;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;

public class EnumTool {
    public static String transString(CourseSelectType courseSelectType){
        if(courseSelectType==null) return null;
        switch (courseSelectType){
            case common: return "通识课程";
            case major:return "专业限制课程";
            default:return "--";
        }
    }

    public static CourseSelectType transCourseSelectType(String str){
        if(str==null)return null;
        switch (str){
            case "通识课程":return CourseSelectType.common;
            case "专业限制课程":return CourseSelectType.major;
        }
        return null;
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

    public static Grade transGrade(String str){
        if(str==null)return null;
        switch (str){
            case "2019":return Grade.G2019;
            case "2020":return Grade.G2020;
            case "2021":return Grade.G2021;
            case "2022":return Grade.G2022;
        }
        return null;
    }

    public static String transString(Semester semester){
        if(semester==null) return null;
        switch (semester){
            case First:return "第1学期";
            case Second:return "第2学期";
            case WinterSem:return "寒假学期";
            case SummerSem:return "暑假学期";
            default:return "--";
        }
    }

    public static Semester transSemester(String str){
        if(str==null) return null;
        switch (str){
            case "第1学期":return Semester.First;
            case "第2学期":return Semester.Second;
            case "寒假学期":return Semester.WinterSem;
            case "暑假学期":return Semester.SummerSem;
            default:return null;
        }
    }

    public static String transString(SchoolYear schoolYear){
        if(schoolYear==null) return null;
        switch (schoolYear){
            case _2019_2020:return "2019-2020";
            case _2020_2021:return "2020-2021";
            case _2020_2022:return "2021-2022";
            case _2022_2023:return "2022-2023";
            default:return "--";
        }
    }

    public static SchoolYear transSchoolYear(String str){
        if(str==null) return null;
        switch (str){
            case "2019-2020":return SchoolYear._2019_2020;
            case "2020-2021":return SchoolYear._2020_2021;
            case "2021-2022":return SchoolYear._2020_2022;
            case "2022-2023":return SchoolYear._2022_2023;
            default:return null;
        }
    }

}
