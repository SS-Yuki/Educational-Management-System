package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import lombok.Data;

import java.util.List;

@Data
public class TeacherCourseApplyingData {
    private Integer id;
    private String courseNumber;
    private String courseName;
    private String classroom ;
    private Integer creditHours;
    private Integer credits;
    private Integer capacity;
    private String introduction;
    private String selectTypeString;
    private List<String> majorLimits;
    private String year;
    private String semester;
    private List<List<Integer>> occupyTime;

    public CourseApplyingData getCourseApply(){
        CourseApplyingData courseApplyingData = new CourseApplyingData();
        courseApplyingData.setId(this.id);
        courseApplyingData.setCourseNumber(this.courseNumber);
        courseApplyingData.setCourseName(this.courseName);
        courseApplyingData.setClassroom(this.classroom);
        courseApplyingData.setCreditHours(this.creditHours);
        courseApplyingData.setCredits(this.getCredits());
        courseApplyingData.setCapacity(this.capacity);
        courseApplyingData.setIntroduction(this.introduction);
        courseApplyingData.setSelectTypeString(this.selectTypeString);
        courseApplyingData.setMajorLimits(this.majorLimits);
        courseApplyingData.setYear(this.year);
        courseApplyingData.setSemester(this.semester);
        courseApplyingData.setOccupyTime(this.occupyTime);
        return courseApplyingData;
    }
}
