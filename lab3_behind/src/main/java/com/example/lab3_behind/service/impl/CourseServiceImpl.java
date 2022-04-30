package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.utils.FormatCheck;
import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseApplyingRepository courseApplyingRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseApplyingRepository courseApplyingRepository,
                             TeacherRepository teacherRepository, StudentRepository studentRepository,
                             SchoolRepository schoolRepository, MajorRepository majorRepository){
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
        this.courseRepository = courseRepository;
        this.courseApplyingRepository = courseApplyingRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<Course> findAPageCourseForSelecting(Integer page, Integer size, String search, String stuNum) throws Exception {

        Student student = studentRepository.findByStuNumber(stuNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        String major = student.getMajor().getName();
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return courseRepository.findAllByMajor(major, pageable);
        }
        Course course = new Course();
        course.setCourseName(search);
        Major major1 = new Major();
        major1.setName(search);
        course.setMajor(major1);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::exact)
                .withIgnorePaths("courseId", "classPeriod", "creditHours", "credits", "capacity", "type", "teacherNum"
                        , "courseNumber", "teacherName", "school", "classroom", "introduction", "courseStatus");
        Example<Course> example = Example.of(course, matcher);
        return courseRepository.findAll(example, pageable);
    }

    @Override
    public Page<Course> findAPageCourseOfTeacher(Integer page, Integer size, String search, String jobNumber) throws Exception {
        Teacher teacher = teacherRepository.findByJobNumber(jobNumber);
        if(teacher == null) {
            throw new Exception("教师不存在");
        }
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return courseRepository.findAllByTeacherNum(jobNumber, pageable);
        }
        Course course = new Course();
        course.setCourseName(search);
        course.setTeacherNum(jobNumber);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase(true)
                .withMatcher("teacherNum", ExampleMatcher.GenericPropertyMatcher::exact)
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("courseId", "classPeriod", "creditHours", "credits", "capacity", "type"
                , "courseNumber", "teacherName", "major", "school", "classroom", "introduction", "courseStatus");
        Example<Course> example = Example.of(course, matcher);
        return courseRepository.findAll(example, pageable);
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
        Major major = new Major();
        course.setMajor(major);
        course.setTeacherName(search);
        School school = new School();
        school.setName(search);
        course.setSchool(school);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacherName", ExampleMatcher.GenericPropertyMatcher::contains)
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
            return courseApplyingRepository.findAllByTeacherNum(jobNum, pageable);
        }
        CourseApplying courseApplying = new CourseApplying();
        courseApplying.setCourseName(search);
        courseApplying.setTeacherNum(jobNum);
        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacherNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("applyId", "classPeriod", "creditHours", "credits", "capacity", "type", "courseId", "applicant"
                        , "courseNumber", "teacherName", "major", "school", "classroom", "introduction");
        Example<CourseApplying> example = Example.of(courseApplying, matcher);
        return courseApplyingRepository.findAll(example, pageable);
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
        Major major = new Major();
        major.setName(search);
        courseApplying.setMajor(major);
        courseApplying.setApplicant(search);
        School school = new School();
        school.setName(search);
        courseApplying.setSchool(school);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teacherNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("applicant", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("applyId", "classPeriod", "creditHours", "credits", "capacity", "type");
        Example<CourseApplying> example = Example.of(courseApplying, matcher);
        return courseApplyingRepository.findAll(example,pageable);
    }

    @Override
    public CourseApplying approveCourseApplying(Integer courseApplyingId) throws Exception {
        CourseApplying courseApplying = courseApplyingRepository.findByApplyId(courseApplyingId);
        if(courseApplying == null){
            throw new Exception("该申请不存在");
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplying.getTeacherNum());
        if(teacher == null){
            throw new Exception("该申请所属老师不存在");
        }
        CourseApplyingData courseApplyingData = new CourseApplyingData(courseApplying);
        if(courseApplying.getType() == CourseApplyingType.Publish){
            this.insertCourse(courseApplyingData);
            teacher.getCoursesApplying().remove(courseApplying);
        } else if(courseApplying.getType() == CourseApplyingType.Change){
            this.updateCourse(courseApplyingData);
            teacher.getCoursesApplying().remove(courseApplying);
        } else if(courseApplying.getType() == CourseApplyingType.Delete){
            this.deleteCourse(courseApplying.getCourseId());
        }
        teacherRepository.save(teacher);
        return courseApplying;
    }

    @Override
    public CourseApplying rejectCourseApplying(Integer courseApplyingId) throws Exception {
        CourseApplying courseApplying = courseApplyingRepository.findByApplyId(courseApplyingId);
        if(courseApplying == null){
            throw new Exception("该申请不存在");
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplying.getTeacherNum());
        if(teacher == null){
            courseApplyingRepository.delete(courseApplying);
            throw new Exception("该申请所属老师不存在");
        }
        teacher.getCoursesApplying().remove(courseApplying);
        teacherRepository.save(teacher);
        return courseApplying;
    }

    @Override
    public CourseApplying pushCourseApplying(CourseApplyingData courseApplyingData, CourseApplyingType applyingType) throws Exception {
        School school = schoolRepository.findByName(courseApplyingData.getSchool());
        if(school == null){
            throw new Exception("申请对应课程所属学院不存在");
        }
        Major major = majorRepository.findByNameAndSchool(courseApplyingData.getMajor(),school);
        if(major == null){
            throw new Exception("申请对应课程所属学院下不存在此专业");
        }
        try {
            FormatCheck.courseApplyingDataCheck(courseApplyingData);
        } catch (Exception e) {
            throw e;
        }
        CourseApplying courseApplying = new CourseApplying((courseApplyingData), school, major);
        courseApplying.setType(applyingType);
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        if(applyingType != CourseApplyingType.Publish){
            CourseApplying oldCourseApplying = courseApplyingRepository.findByCourseId(courseApplyingData.getId());
            if(oldCourseApplying != null){
                throw new Exception("该课程已有申请正在审核");
            }
        }
        teacher.getCoursesApplying().add(courseApplying);
        teacherRepository.save(teacher);
        return courseApplying;
    }

    @Override
    public Course insertCourse(CourseApplyingData courseApplyingData) throws Exception {
        try {
            FormatCheck.courseApplyingDataCheck(courseApplyingData);
        } catch (Exception e) {
            throw e;
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        School school = schoolRepository.findByName(courseApplyingData.getSchool());
        if(school == null){
            throw new Exception("课程所属学院不存在");
        }
        Major major = majorRepository.findByNameAndSchool(courseApplyingData.getMajor(),school);
        if(major == null){
            throw new Exception("课程所属学院下不存在此专业");
        }
        Course course = new Course(courseApplyingData, school,major);
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);
        return course;
    }

    @Override
    public Course updateCourse(CourseApplyingData courseApplyingData) throws Exception {
        try {
            FormatCheck.courseApplyingDataCheck(courseApplyingData);
        } catch (Exception e) {
            throw e;
        }
        Course course = courseRepository.findByCourseId(courseApplyingData.getId());
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
        thisCourse.setMajor(majorRepository.findByName(courseApplyingData.getMajor()));
        thisCourse.setSchool(schoolRepository.findByName(courseApplyingData.getSchool()));
        thisCourse.setCredits(courseApplyingData.getCredits());
        thisCourse.setCreditHours(courseApplyingData.getCreditHours());
        thisCourse.setClassroom(courseApplyingData.getClassroom());
        teacherRepository.save(teacher);
        return thisCourse;
    }

    @Override
    public Course deleteCourse(Integer courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("所删除课程不存在，或已被删除");
        }
        CourseApplying courseApplying = courseApplyingRepository.findByCourseId(courseId);
        Teacher teacher = teacherRepository.findByJobNumber(course.getTeacherNum());
        if(courseApplying!=null){
            teacher.getCoursesApplying().remove(courseApplying);
        }
        teacher.getCourses().remove(course);
        teacherRepository.save(teacher);
        return course;
    }
}
