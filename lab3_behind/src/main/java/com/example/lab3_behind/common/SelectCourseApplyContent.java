package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.SelectCourseApplication;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.EnumTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectCourseApplyContent {
    Integer courseId;
    String courseNum;
    String courseName;
    String teacherName;
    String year;
    String semester;
    String description;
    String dealStatus;
    String advice;

    public static List<SelectCourseApplyContent> getContents(List<SelectCourseApplication> applys, CourseService courseService) throws Exception {
        List<SelectCourseApplyContent> applyContents = new ArrayList<>();
        for(SelectCourseApplication apply:applys){
            Course course = courseService.getCourse(apply.getCourseId());
            SelectCourseApplyContent temp = new SelectCourseApplyContent(
                    apply.getCourseId(),
                    course.getTeacherNum(),
                    course.getCourseName(),
                    course.getTeacherName(),
                    EnumTool.transString(course.getSchoolYear()),
                    EnumTool.transString(course.getSemester()),
                    apply.getReason(),
                    EnumTool.transString(apply.getStatus()),
                    apply.getFeedBack()
            );
            applyContents.add(temp);
        }
        return applyContents;
    }
}
