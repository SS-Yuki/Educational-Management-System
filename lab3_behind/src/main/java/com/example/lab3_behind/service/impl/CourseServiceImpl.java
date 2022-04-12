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

        courseApplying.setIntroduction(search);
        courseApplying.setMajor(search);
        courseApplying.setApplicant(search);
        courseApplying.setSchool(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacherNum", ExampleMatcher.GenericPropertyMatcher::contains)
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
        // TODO: 2022/4/12
        return course;
    }

    @Override
    public CourseApplying pushCourseApplying(CourseApplyingData courseApplyingData, CourseApplyingType applyingType) throws Exception {
        CourseApplying courseApplying = new CourseApplying((courseApplyingData));
        courseApplying.setType(applyingType);
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        teacher.getCoursesApplying().add(courseApplying);
        teacherRepository.save(teacher);
        return courseApplying;
    }

    @Override
    public Course insertCourse(CourseApplyingData courseApplyingData) throws Exception {
        Course course = new Course(courseApplyingData);
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);
        return course;
    }

    @Override
    public Course updateCourse(CourseApplyingData courseApplyingData) throws Exception {
        Course course = courseRepository.findById(courseApplyingData.getId());
        if(course == null){
            throw new Exception("所修改课程不存在，或已被删除");
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("所修改课程的教师不存在");
        }
        Course thisCourse = teacher.getCourses().get(teacher.getCourses().indexOf(course));
        thisCourse.setCourseName(courseApplyingData.getCourseName());
        thisCourse.setCourseNumber(courseApplyingData.getCourseNumber());
        thisCourse.setIntroduction(courseApplyingData.getIntroduction());
        thisCourse.setCapacity(courseApplyingData.getCapacity());
        thisCourse.setClassPeriod(courseApplyingData.getClassPeriod());
        thisCourse.setMajor(courseApplyingData.getMajor());
        thisCourse.setSchool(courseApplyingData.getSchool());
        thisCourse.setCredits(courseApplyingData.getCredits());
        thisCourse.setCreditHours(courseApplyingData.getCreditHours());
        thisCourse.setClassroom(courseApplyingData.getClassroom());
        teacherRepository.save(teacher);
        return thisCourse;
    }

    @Override
    public Course deleteCourse(Integer courseId) throws Exception {
        Course course = courseRepository.findById(courseId);
        if(course == null){
            throw new Exception("所删除课程不存在，或已被删除");
        }
        Teacher teacher = teacherRepository.findByJobNumber(course.getTeacherNum());
        teacher.getCourses().remove(course);
        teacherRepository.save(teacher);
        return course;
    }
}
