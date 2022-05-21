package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.MyPage;
import com.example.lab3_behind.common.forDomain.*;
import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.service.CourseSelectingService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.MyPageTool;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab3_behind.service.impl.StudentServiceImpl.getCourses;
import static com.example.lab3_behind.service.impl.StudentServiceImpl.getSchedule;

@Service
public class CourseSelectingServiceImpl implements CourseSelectingService {
    CourseRepository courseRepository;
    StudentRepository studentRepository;
    TimeTableRepository timeTableRepository;
    AuthorityRepository authorityRepository;
    ClassroomRepository classroomRepository;
    CourseSelectingRecordRepository courseSelectingRecordRepository;
    SelectCourseApplicationRepository selectCourseApplicationRepository;
    @Autowired
    CourseSelectingServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository,
                               TimeTableRepository timeTableRepository, AuthorityRepository authorityRepository,
                               CourseSelectingRecordRepository courseSelectingRecordRepository, SelectCourseApplicationRepository selectCourseApplicationRepository,
                               ClassroomRepository classroomRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.timeTableRepository = timeTableRepository;
        this.authorityRepository = authorityRepository;
        this.courseSelectingRecordRepository = courseSelectingRecordRepository;
        this.selectCourseApplicationRepository = selectCourseApplicationRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    public void selectCourse(String stuNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception {
        //检查course是否为open学期；如果不是一轮选课，容量是否已满；选课权限；专业限制；同类课程（课程代码和课程名称相同的课程）同⼀个学⽣只能选⼀⻔,学⽣选课时已经修过的课程不可再选
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        Student student = studentRepository.findByStuNumber(stuNum);
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
        List<Course> allCourse = getCourses(student);
        for (Course tempCourse : allCourse){
            if(tempCourse.getCourseId().equals(courseId)){
                throw new Exception("无法选择已经选过的课程");
            }
            if(tempCourse.getCourseNumber().equals(course.getCourseNumber())){
                throw new Exception("同类课程只能修读一门");
            }
        }
        //课程人数,学生学分
        course.setStudentsNum(course.getStudentsNum() + 1);
        student.setCredits(student.getCredits() + course.getCredits());
        //时间冲突
        TimeTool.addTimeMatrix(getSchedule(studentRepository, timeTableRepository, stuNum, openYear, openSemester),
                TimeTool.makeTimeMatrix(course.getClassTime()));
        courseRepository.save(course);
        CourseSelectingRecord courseSelectingRecord = new CourseSelectingRecord(null , course, student, 0,StudyStatus.ToStudy );
        courseSelectingRecordRepository.save(courseSelectingRecord);

//        student.getCourses().add(course);
//        studentRepository.save(student);
//
//        CourseSelectingRecord courseSelectingRecord = courseSelectingRecordRepository.findByStudentAndCourse(student, course);
//        courseSelectingRecord.setStudyStatus(StudyStatus.ToStudy);
//        courseSelectingRecordRepository.save(courseSelectingRecord);
    }

    @Override
    public void dropCourse(String stuNum, Integer courseId, SchoolYear openYear, Semester openSemester) throws Exception {
        //检查course是否为open学期；是否选了
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        Student student = studentRepository.findByStuNumber(stuNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        Authority selectRound = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if(selectRound.getAuthorityValue().equals(Global.NOT_IN_COURSE_SELECTING_ROUND)){
            throw new Exception("当前不可退课");
        }
        if(!getCourses(student).contains(course)){
            throw new Exception("无法退掉未选上的课程");
        }
        //课程人数,学生学分
        course.setStudentsNum(course.getStudentsNum() - 1);
        student.setCredits(student.getCredits() - course.getCredits());
        courseRepository.save(course);
        CourseSelectingRecord record = courseSelectingRecordRepository.findByStudentAndCourse(student, course);
        student.getRecords().remove(record);
        studentRepository.save(student);
        course.getRecords().remove(record);
        courseRepository.save(course);
        courseSelectingRecordRepository.delete(record);
    }

    @Override
    public void pushCourseSelectingApplication(String stuNum, Integer courseId, String reason) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        Student student = studentRepository.findByStuNumber(stuNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        List<SelectCourseApplication> applications = selectCourseApplicationRepository.findByStuNumberAndCourseId(stuNum, courseId);
        for(SelectCourseApplication application : applications){
            if(application.getStatus().equals(SelectCourseApplicationStatus.ToDeal)){
                throw new Exception("已有正在审核的对这门课程的申请");
            }
        }
        Authority selectRound = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if(selectRound.getAuthorityValue().equals(Global.NOT_IN_COURSE_SELECTING_ROUND)){
            throw new Exception("当前不可提交选课申请");
        }
        if(selectRound.getAuthorityValue().equals(Global.FIRST_COURSE_SELECTING_ROUND)){
            throw new Exception("当前为一轮选课，请先自主选课");
        }
        boolean isMajor = course.getCourseSelectType().equals(CourseSelectType.common) || course.getMajorsOptional().contains(student.getMajor());
        YearSemesterPair timePair = TimeTool.getPresentYearAndSemester();
        if((EnumTool.transString(course.getSchoolYear()).equals(timePair.getYear())
                && EnumTool.transString(course.getSemester()).equals(timePair.getSemester()))){
            if((course.getCapacity().compareTo(course.getStudentsNum()) > 0) && isMajor){
                throw new Exception("该门课程容量未满，不必打申请");
            }
        }
        SchoolYear schoolYear = course.getSchoolYear();
        Semester semester = course.getSemester();
        //时间冲突
        TimeTool.addTimeMatrix(getSchedule(studentRepository, timeTableRepository, stuNum, schoolYear, semester),
                TimeTool.makeTimeMatrix(course.getClassTime()));

        for(Course oldCourse : getCourses(student)){
            if(oldCourse.getSchoolYear().equals(schoolYear) && oldCourse.getSemester().equals(semester)
                    && oldCourse.getCourseNumber().equals(course.getCourseName())){
                throw new Exception("同类课程只能修读一门");
            }
        }

        SelectCourseApplication application = new SelectCourseApplication(null, stuNum, courseId, reason, null, SelectCourseApplicationStatus.ToDeal);
        selectCourseApplicationRepository.save(application);
    }

    @Override
    public void approveSelectCourseApplication(Integer applicationId, String commends) throws Exception {
        SelectCourseApplication application = selectCourseApplicationRepository.findById(applicationId);
        if(application == null){
            throw new Exception("该申请不存在");
        }
        Student student = studentRepository.findByStuNumber(application.getStuNumber());
        if(student == null){
            throw new Exception("学生不存在");
        }
        Course course = courseRepository.findByCourseId(application.getCourseId());
        if(course == null){
            throw new Exception("课程不存在");
        }
        Classroom classroom = course.getClassroom();
        if(course.getCapacity().compareTo(classroom.getCapacity()) == 0){
            throw new Exception("教室容量已满，无法再为此课程增加学生");
        } else {
            course.setCapacity(course.getCapacity() + 1);
            course.setStudentsNum(course.getStudentsNum() + 1);
        }
        //时间冲突
        TimeTool.addTimeMatrix(getSchedule(studentRepository, timeTableRepository, student.getStuNumber(), course.getSchoolYear(), course.getSemester()),
                TimeTool.makeTimeMatrix(course.getClassTime()));

        courseRepository.save(course);
        CourseSelectingRecord courseSelectingRecord = new CourseSelectingRecord(null , course, student, 0,StudyStatus.ToStudy);
        courseSelectingRecordRepository.save(courseSelectingRecord);

        application.setStatus(SelectCourseApplicationStatus.Approved);
        selectCourseApplicationRepository.save(application);
    }

    @Override
    public void rejectSelectCourseApplication(Integer applicationId, String commends) throws Exception {
        SelectCourseApplication application = selectCourseApplicationRepository.findById(applicationId);
        if(application == null){
            throw new Exception("该申请不存在");
        }
        application.setStatus(SelectCourseApplicationStatus.Rejected);
        selectCourseApplicationRepository.save(application);
    }

    @Override
    public void RandomSelectFirstRound() throws Exception {
        Authority select = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if(select.getAuthorityValue().equals("true")){
            throw new Exception("请先关闭学生选课权限");
        }
        Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if(!round.getAuthorityValue().equals(Global.FIRST_COURSE_SELECTING_ROUND)){
            throw new Exception("只能在一轮选课进行此操作");
        }
        List<Course> allCourse = courseRepository.findByCourseStatus(CourseStatus.Published);
        for (Course course : allCourse){
            List<CourseSelectingRecord> records = courseSelectingRecordRepository.findByCourse(course);
            Integer nowNumber = 0;
            boolean isFull = false;
            for (Grade grade : Grade.values()){
                for (CourseSelectingRecord record : records){
                    if(isFull){
                        course.getRecords().remove(record);
                        courseRepository.save(course);
                        Student student =  record.getStudent();
                        student.getRecords().remove(record);
                        studentRepository.save(student);
                        courseSelectingRecordRepository.delete(record);
                        continue;
                    }
                    Student student = record.getStudent();
                    if(student.getGrade().equals(grade)){
                        nowNumber += 1;
                    }
                    if(nowNumber.compareTo(course.getCapacity()) == 0){
                        isFull = true;
                    }
                }
            }
            if(isFull){
                course.setStudentsNum(course.getCapacity());
                courseRepository.save(course);
            }
        }
    }

    @Override
    public MyPage<SelectCourseApplication> findAPageSelectCourseApplicationToDeal(Integer page, Integer size) {
        List<SelectCourseApplication> allApplication = selectCourseApplicationRepository.findAll();
        List<SelectCourseApplication> result = new ArrayList<>();
        for (SelectCourseApplication application : allApplication){
            if (application.getStatus().equals(SelectCourseApplicationStatus.ToDeal)){
                result.add(application);
            }
        }
        return MyPageTool.getPage(result, size, page);
    }

    @Override
    public List<SelectCourseApplication> findMySelectCourseApplication(String stuNum) {
        return selectCourseApplicationRepository.findByStuNumber(stuNum);
    }

}
