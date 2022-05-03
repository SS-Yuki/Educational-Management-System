package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.service.CourseSelectingService;
import org.springframework.stereotype.Service;

@Service
public class CourseSelectingServiceImpl implements CourseSelectingService {
    @Override
    public void selectCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) {
        //检查course是否为open学期；如果不是一轮选课，容量是否已满；选课权限；专业限制；同类课程（课程代码和课程名称相同的课程）同⼀个学⽣只能选⼀⻔,学⽣选课时已经修过的课程不可再选
    }

    @Override
    public void dropCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) {
        //检查course是否为open学期；是否选了
    }
}
