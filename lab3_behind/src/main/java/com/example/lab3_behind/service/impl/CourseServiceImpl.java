package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.CourseApplyingType;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.repository.CourseApplyingRepository;
import com.example.lab3_behind.repository.CourseRepository;
import com.example.lab3_behind.repository.TeacherRepository;
import com.example.lab3_behind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseApplyingRepository courseApplyingRepository;
    TeacherRepository teacherRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseApplyingRepository courseApplyingRepository, TeacherRepository teacherRepository){
        this.courseRepository = courseRepository;
        this.courseApplyingRepository = courseApplyingRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Page<Course> findAPageCourse(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return courseRepository.findAll(pageable);
        }
        Course course = new Course();
        course.setCourseName(search);
        course.setTeacherNum(search);
        course.setCourseNumber(search);
        course.setIntroduction(search);
        course.setMajor(search);
        course.setTeacherName(search);
        course.setSchool(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacher", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacherName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "classPeriod", "creditHours", "credits", "capacity", "type");
        Example<Course> example = Example.of(course, matcher);
        return courseRepository.findAll(example,pageable);
    }

    @Override
    public Page<CourseApplying> findCourseApplyingOfTeacher(Integer page, Integer size, String search, String jobNum) throws Exception {
        Teacher teacher = teacherRepository.findByJobNumber(jobNum);
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return courseApplyingRepository.findAll(pageable);
        }
        CourseApplying courseApplying = new CourseApplying();
        courseApplying.setCourseName(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "classPeriod", "creditHours", "credits", "capacity", "type");
        Example<CourseApplying> example = Example.of(courseApplying, matcher);
        return courseApplyingRepository.findAllByTeacherNum(jobNum, example, pageable);
    }

    @Override
    public Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return courseApplyingRepository.findAll(pageable);
        }
        CourseApplying courseApplying = new CourseApplying();
        courseApplying.setCourseName(search);
        courseApplying.setTeacherNum(search);
        courseApplying.setCourseNumber(search);
        courseApplying.setIntroduction(search);
        courseApplying.setMajor(search);
        courseApplying.setApplicant(search);
        courseApplying.setSchool(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacher", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("applicant", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "classPeriod", "creditHours", "credits", "capacity", "type");
        Example<CourseApplying> example = Example.of(courseApplying, matcher);
        return courseApplyingRepository.findAll(example,pageable);
    }

    @Override
    public Course ApproveCourseApplying(Integer courseApplyingId) throws Exception {
        CourseApplying courseApplying = courseApplyingRepository.findById(courseApplyingId);
        if(courseApplying == null){
            throw new Exception("该申请不存在");
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplying.getTeacherNum());
        Course course = new Course(courseApplying);
        teacher.getCourses().add(course);
        return course;
    }

    @Override
    public CourseApplying pushCourseApplying(CourseApplyingData courseData, CourseApplyingType applyingType) throws Exception {
        CourseApplying courseApplying = new CourseApplying((courseData));
        courseApplying.setType(applyingType);
        Teacher teacher = teacherRepository.findByJobNumber(courseData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        teacher.getCoursesApplying().add(courseApplying);
        teacherRepository.save(teacher);
        return courseApplying;
    }
//    @Override
//    public Course insertCourse(CourseApplyingData courseApplyingData){
//
//    }
//
//    @Override
//    public Course updateCourse(CourseApplyingData courseApplyingData){}
//
//    @Override
//    public Course deleteCourse(Integer courseId){}
}
