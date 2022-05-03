package com.example.lab3_behind.service;

import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;

public interface CourseSelectingService {
    void selectCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception;
    void dropCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception;
}
