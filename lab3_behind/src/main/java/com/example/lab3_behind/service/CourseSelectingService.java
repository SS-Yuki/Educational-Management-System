package com.example.lab3_behind.service;

import com.example.lab3_behind.common.MyPage;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.SelectCourseApplication;

import java.util.List;

public interface CourseSelectingService {
    void selectCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception;
    void dropCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception;
    void pushCourseSelectingApplication(String stuNum, Integer courseId);
    void approveSelectCourseApplication(Integer applicationId, String commends);
    void rejectSelectCourseApplication(Integer applicationId, String commends);
    MyPage<SelectCourseApplication> findAPageSelectCourseApplicationToDeal();
    List<SelectCourseApplication> findMySelectCourseApplication(String stuNum);
}
