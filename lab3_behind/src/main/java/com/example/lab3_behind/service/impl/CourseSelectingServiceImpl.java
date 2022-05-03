package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.*;
import com.example.lab3_behind.domain.Authority;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseSelectingRecord;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.service.CourseSelectingService;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.lab3_behind.service.impl.StudentServiceImpl.getSchedule;

@Service
public class CourseSelectingServiceImpl implements CourseSelectingService {
    CourseRepository courseRepository;
    StudentRepository studentRepository;
    TimeTableRepository timeTableRepository;
    AuthorityRepository authorityRepository;
    CourseSelectingRecordRepository courseSelectingRecordRepository;
    @Autowired
    CourseSelectingServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                               TimeTableRepository timeTableRepository, AuthorityRepository authorityRepository,
                               CourseSelectingRecordRepository courseSelectingRecordRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.timeTableRepository = timeTableRepository;
        this.authorityRepository = authorityRepository;
        this.courseSelectingRecordRepository = courseSelectingRecordRepository;
    }

    @Override
    public void selectCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception {
        //检查course是否为open学期；如果不是一轮选课，容量是否已满；选课权限；专业限制；同类课程（课程代码和课程名称相同的课程）同⼀个学⽣只能选⼀⻔,学⽣选课时已经修过的课程不可再选
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        Student student = studentRepository.findByStuNumber(sutNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        Authority selectAuthority = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if(selectAuthority.getAuthorityValue().equals("false")){
            throw new Exception("当前不可选课");
        }
        Authority selectRound = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if(!selectRound.getAuthorityValue().equals(Global.FIRST_COURSE_SELECTING_ROUND)){
            if(course.getCapacity().compareTo(course.getStudentsNum()) <= 0){
                throw new Exception("该课程容量已满");
            }
        }
        if(!course.getSchoolYear().equals(openYear) || !course.getSemester().equals(openSemester)){
            throw new Exception("所选课程所在学期不在可选范围内");
        }
        if(!course.getCourseSelectType().equals(CourseSelectType.common) && !course.getMajorsOptional().contains(student.getMajor())){
            throw new Exception("由于专业限制无法选择此门课程");
        }
        List<Course> allCourse = student.getCourses();
        for (Course tempCourse : allCourse){
            if(tempCourse.getCourseId().equals(courseId)){
                throw new Exception("无法选择已经选过的课程");
            }
            if(tempCourse.getCourseNumber().equals(course.getCourseNumber())
            && tempCourse.getCourseName().equals(course.getCourseName())){
                throw new Exception("同类课程只能修读一门");
            }
        }
        //课程人数,学生学分
        course.setStudentsNum(course.getStudentsNum() + 1);
        courseRepository.save(course);
        student.setCredits(student.getCredits() + course.getCredits());
        //时间冲突
        TimeTool.addTimeMatrix(getSchedule(studentRepository, timeTableRepository, sutNum, openYear, openSemester),
                TimeTool.makeTimeMatrix(course.getClassTime()));

        student.getCourses().add(course);
        studentRepository.save(student);

        CourseSelectingRecord courseSelectingRecord = courseSelectingRecordRepository.findByStudentAndCourse(student, course);
        courseSelectingRecord.setStudyStatus(StudyStatus.ToStudy);
        courseSelectingRecordRepository.save(courseSelectingRecord);
    }

    @Override
    public void dropCourse(String sutNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception {
        //检查course是否为open学期；是否选了
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        Student student = studentRepository.findByStuNumber(sutNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        Authority selectRound = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if(selectRound.getAuthorityValue().equals(Global.NOT_IN_COURSE_SELECTING_ROUND)){
            throw new Exception("当前不可退课");
        }
        if(!student.getCourses().contains(course)){
            throw new Exception("无法退掉未选上的课程");
        }
        //课程人数,学生学分
        course.setStudentsNum(course.getStudentsNum() - 1);
        courseRepository.save(course);
        student.setCredits(student.getCredits() - course.getCredits());

        student.getCourses().remove(course);
        studentRepository.save(student);
    }
}
