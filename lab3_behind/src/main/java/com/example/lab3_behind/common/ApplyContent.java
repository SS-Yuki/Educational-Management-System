package com.example.lab3_behind.common;


import com.example.lab3_behind.domain.CourseApplying;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplyContent {
    private Integer applyId;
    private Integer courseId;
    private String courseNumber;
    private String courseName;
    private String teacherNum;
    private String teacherName;
    private String major;
    private String school;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;
    private String applicant;
    private String applyType;

    public static List<ApplyContent> getContents(List<CourseApplying> courseApplyings){
        List<ApplyContent> applyContents = new ArrayList<>();
        for(CourseApplying courseApplying:courseApplyings){
            String myApplyType;
            switch (courseApplying.getType()){
                case Delete:myApplyType = "删除课程";break;
                case Change:myApplyType = "修改信息";break;
                case Publish:myApplyType = "新增课程";break;
                default: myApplyType = "未声明";
            }

            ApplyContent temp = new ApplyContent(
                    courseApplying.getApplyId(),
                    courseApplying.getCourseId(),
                    courseApplying.getCourseNumber(),
                    courseApplying.getCourseName(),
                    courseApplying.getTeacherNum(),
                    courseApplying.getTeacherName(),
                    courseApplying.getMajor().getName(),
                    courseApplying.getSchool().getName(),
                    courseApplying.getClassroom().getName(),
                    courseApplying.getCreditHours(),
                    courseApplying.getCredits(),
                    courseApplying.getCapacity(),
                    courseApplying.getIntroduction(),
                    courseApplying.getApplicant(),
                    myApplyType
                    );
            applyContents.add(temp);
        }
        return applyContents;
    }

}
