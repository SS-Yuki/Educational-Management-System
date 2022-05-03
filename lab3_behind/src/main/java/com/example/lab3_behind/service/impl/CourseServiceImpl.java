package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.FormatCheck;
import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseApplyingRepository courseApplyingRepository;
    TeacherRepository teacherRepository;
    StudentRepository studentRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    ClassroomRepository classroomRepository;
    TimeTableRepository timeTableRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseApplyingRepository courseApplyingRepository,
                             TeacherRepository teacherRepository, StudentRepository studentRepository,
                             SchoolRepository schoolRepository, MajorRepository majorRepository,
                             ClassroomRepository classroomRepository, TimeTableRepository timeTableRepository){
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
        this.courseRepository = courseRepository;
        this.courseApplyingRepository = courseApplyingRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.timeTableRepository = timeTableRepository;
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
    public Course getCourse(Integer courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("该课程不存在");
        }
        return course;
    }

    @Override
    public Page<Course> findAPageCourse(Integer page, Integer size, String search, SchoolYear schoolYear, Semester semester){

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
            this.insertCourse(courseApplying);
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
        Classroom classroom = classroomRepository.findByName(courseApplyingData.getClassroom());
        List<Major> majorsOptional = new ArrayList<>();
        for (String str : courseApplyingData.getMajorLimits()){
            majorsOptional.add(majorRepository.findByName(str));
        }
        String classTime = TimeTool.transSchedule(TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(),
                TimeTool.getSectionNum(classroom.getSchedule()), Global.COURSE_MAX));
        CourseApplying courseApplying = new CourseApplying((courseApplyingData), school, major, classroom, majorsOptional, classTime);
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
        Classroom classroom = classroomRepository.findByName(courseApplyingData.getClassroom());
        try {
            //此处检测时间冲突
            TimeTool.addTimeMatrix(
                    this.getClassroomTime(courseApplyingData.getClassroom(),
                            EnumTool.transSchoolYear(courseApplyingData.getYear()),
                            EnumTool.transSemester(courseApplyingData.getSemester())),
                    TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(), TimeTool.getSectionNum(classroom.getSchedule()), -1));
        } catch (Exception e) {
            throw e;
        }
        List<Major> majorsOptional = new ArrayList<>();
        for (String str : courseApplyingData.getMajorLimits()){
            majorsOptional.add(majorRepository.findByName(str));
        }
        Course course = new Course(courseApplyingData, school, major, classroom, majorsOptional, teacher.getName());
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);

        //时间表处理：
        Course newCourse = courseRepository.findByCourseNumberAndTeacherNumAndSchoolYearAndSemester(
                course.getCourseNumber(), course.getTeacherNum(), course.getSchoolYear(), course.getSemester()
        );
        List<List<Integer>> classSchedule;
        classSchedule = TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(), TimeTool.getSectionNum(classroom.getSchedule()), newCourse.getCourseId());
        newCourse.setClassTime(TimeTool.transSchedule(classSchedule));
        courseRepository.save(newCourse);
        YearSemesterPair yearAndSemester = TimeTool.getPresentYearAndSemester();
        List<List<Integer>> timeMatrix;
        if((newCourse.getSchoolYear() == EnumTool.transSchoolYear(yearAndSemester.getYear()))
                &&(newCourse.getSemester() == EnumTool.transSemester(yearAndSemester.getSemester()))){
            try {
                timeMatrix = TimeTool.addTimeMatrix(TimeTool.makeTimeMatrix(classroom.getSchedule()), classSchedule);
            } catch (Exception e) {
                throw e;
            }
            classroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(classroom);
        }

        return newCourse;
    }

    private Course insertCourse(CourseApplying courseApplying) throws Exception {
        Classroom classroom = classroomRepository.findByName(courseApplying.getClassroom().getName());
        try {
            //此处检测时间冲突
            TimeTool.addTimeMatrix(
                    this.getClassroomTime(courseApplying.getClassroom().getName(),
                            courseApplying.getSchoolYear(),
                            courseApplying.getSemester()),
                    TimeTool.makeTimeMatrix(courseApplying.getClassTime()));
        } catch (Exception e) {
            throw e;
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplying.getTeacherNum());
        Course course = new Course(courseApplying);
        teacher.getCourses().add(course);
        teacherRepository.save(teacher);
        Course newCourse = courseRepository.findByCourseNumberAndTeacherNumAndSchoolYearAndSemester(
                course.getCourseNumber(), course.getTeacherNum(), course.getSchoolYear(), course.getSemester()
        );
        newCourse.setClassTime(TimeTool.transSchedule(TimeTool.transMaxInSchedule(newCourse.getClassTime(), newCourse.getCourseId())));
        courseRepository.save(newCourse);

        YearSemesterPair yearAndSemester = TimeTool.getPresentYearAndSemester();
        if((newCourse.getSchoolYear() == EnumTool.transSchoolYear(yearAndSemester.getYear()))
                &&(newCourse.getSemester() == EnumTool.transSemester(yearAndSemester.getSemester()))){
            try {
                classroom.setSchedule(TimeTool.transSchedule(
                        TimeTool.addTimeMatrix(TimeTool.makeTimeMatrix(classroom.getSchedule()),
                                TimeTool.makeTimeMatrix(newCourse.getClassTime()))
                ));
            } catch (Exception e) {
                throw e;
            }
            classroomRepository.save(classroom);
        }
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
        Classroom oldClassroom = course.getClassroom();
        Classroom newClassroom = classroomRepository.findByName(courseApplyingData.getClassroom());
        YearSemesterPair yearAndSemester = TimeTool.getPresentYearAndSemester();
        List<List<Integer>> thisCourseSchedule = TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(), this.getLastSection(), course.getCourseId());
        if((course.getSchoolYear() == EnumTool.transSchoolYear(yearAndSemester.getYear()))
                &&(course.getSemester() == EnumTool.transSemester(yearAndSemester.getSemester()))){
            List<List<Integer>> timeMatrix =  TimeTool.subTimeMatrix(TimeTool.makeTimeMatrix(oldClassroom.getSchedule()), course.getCourseId());
            oldClassroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(oldClassroom);
            try {
                //此处检测时间冲突
                timeMatrix = TimeTool.addTimeMatrix(timeMatrix, thisCourseSchedule);
            } catch (Exception e) {
                throw e;
            }
            newClassroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(newClassroom);
        } else {
            try {
                List<List<Integer>> otherTimeMatrix = TimeTool.subTimeMatrix(
                        this.getClassroomTime(courseApplyingData.getClassroom(),
                                EnumTool.transSchoolYear(courseApplyingData.getYear()),
                                EnumTool.transSemester(courseApplyingData.getSemester())),
                        course.getCourseId());
                //此处检测时间冲突
                 TimeTool.addTimeMatrix(otherTimeMatrix, thisCourseSchedule);
            } catch (Exception e) {
                throw e;
            }
        }
        Course thisCourse = teacher.getCourses().get(teacher.getCourses().indexOf(course));
        thisCourse.setClassTime(TimeTool.transSchedule(thisCourseSchedule));
        thisCourse.setCourseName(courseApplyingData.getCourseName());
        thisCourse.setCourseNumber(courseApplyingData.getCourseNumber());
        thisCourse.setIntroduction(courseApplyingData.getIntroduction());
        thisCourse.setCapacity(courseApplyingData.getCapacity());
        thisCourse.setMajor(majorRepository.findByName(courseApplyingData.getMajor()));
        thisCourse.setSchool(schoolRepository.findByName(courseApplyingData.getSchool()));
        thisCourse.setCredits(courseApplyingData.getCredits());
        thisCourse.setCreditHours(courseApplyingData.getCreditHours());
        thisCourse.setClassroom(classroomRepository.findByName(courseApplyingData.getClassroom()));
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

        YearSemesterPair yearAndSemester = TimeTool.getPresentYearAndSemester();
        if((course.getSchoolYear() == EnumTool.transSchoolYear(yearAndSemester.getYear()))
                &&(course.getSemester() == EnumTool.transSemester(yearAndSemester.getSemester()))){
            Classroom classroom = course.getClassroom();
            List<List<Integer>> timeMatrix = TimeTool.subTimeMatrix(TimeTool.makeTimeMatrix(classroom.getSchedule()), courseId);
            classroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(classroom);
        }

        teacher.getCourses().remove(course);
        teacherRepository.save(teacher);
        return course;
    }

    private List<List<Integer>> getClassroomTime(String name, SchoolYear schoolYear, Semester semester) throws Exception {
        Classroom classroom = classroomRepository.findByName(name);
        if(classroom == null){
            throw new Exception("教室不存在");
        }
        List<Course> allCourse = courseRepository.findByClassroomAndSchoolYearAndSemester(classroom, schoolYear, semester);
        List<List<Integer>> scheduleList = TimeTool.getEmptyTimeMatrix(this.getLastSection());
        for (int i = 0; i < allCourse.size(); i++){
            try {
                scheduleList = TimeTool.addTimeMatrix(scheduleList, TimeTool.makeTimeMatrix(allCourse.get(i).getClassTime()));
            } catch (Exception e){
                throw e;
            }
        }
        return scheduleList;
    }

    private Integer getLastSection(){
        Integer last = 0;
        List<TimeTable> timeTables = timeTableRepository.findAll();
        for(TimeTable timeTable : timeTables){
            Integer temp = timeTable.getSection();
            if(temp.compareTo(last) >= 0){
                last = temp;
            }
        }
        return last;
    }
}
