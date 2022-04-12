package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import lombok.Data;

@Data
public class TeacherCourseApplyingData {
    private Integer id;
    private String courseNumber;
    private String courseName;
    private String classPeriod ;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;

    public CourseApplyingData getCourseApply(){
        CourseApplyingData courseApplyingData = new CourseApplyingData();
        courseApplyingData.setId(this.id);
        courseApplyingData.setCourseNumber(this.courseNumber);
        courseApplyingData.setCourseName(this.courseName);
        courseApplyingData.setClassPeriod(this.getClassPeriod());
        courseApplyingData.setClassroom(this.classroom);
        courseApplyingData.setCreditHours(this.creditHours);
        courseApplyingData.setCredits(this.getCredits());
        courseApplyingData.setCapacity(this.capacity);
        courseApplyingData.setIntroduction(this.introduction);
        return courseApplyingData;
    }
}
