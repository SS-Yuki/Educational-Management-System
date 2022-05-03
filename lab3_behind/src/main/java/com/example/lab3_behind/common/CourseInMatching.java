package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInMatching {
    private Course course;
    private CourseMatchItem courseMatchItem;

    public static List<Course> toCourseList(List<CourseInMatching> courseInMatching){
        List<Course> result = new ArrayList<>();
        for(CourseInMatching i : courseInMatching){
            result.add(i.getCourse());
        }
        return result;
    }

    public boolean moreRelevanceThan(CourseInMatching courseInMatching, String search){
        String thisMatching = this.getMatchingString();
        String otherMatching = courseInMatching.getMatchingString();
        assert thisMatching != null;
        int thisDifference = Math.abs(thisMatching.length() - search.length());
        assert otherMatching != null;
        int otherDifference = Math.abs(otherMatching.length() - search.length());
        return thisDifference > otherDifference;
    }

    private String getMatchingString(){
        switch (this.courseMatchItem) {
            case COURSE_NAME: return this.course.getCourseName();
            case COURSE_NUMBER: return this.course.getCourseNumber();
            case TEACHER_NAME: return this.course.getTeacherName();
            default: return null;
        }
    }
}
