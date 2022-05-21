package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.CourseInMatching;
import com.example.lab3_behind.common.CourseMatchItem;
import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.MyPage;
import com.example.lab3_behind.common.forDomain.*;
import com.example.lab3_behind.domain.dto.YearSemesterPair;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.FormatCheck;
import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.service.CourseService;
import com.example.lab3_behind.utils.MyPageTool;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab3_behind.common.CourseInMatching.toCourseList;
import static com.example.lab3_behind.service.impl.TeachingAffairsServiceImpl.getSections;


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
    CourseSelectingRecordRepository courseSelectingRecordRepository;
    AuthorityRepository authorityRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseApplyingRepository courseApplyingRepository,
                             TeacherRepository teacherRepository, StudentRepository studentRepository,
                             SchoolRepository schoolRepository, MajorRepository majorRepository,
                             ClassroomRepository classroomRepository, TimeTableRepository timeTableRepository,
                             CourseSelectingRecordRepository courseSelectingRecordRepository, AuthorityRepository authorityRepository){
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
        this.courseRepository = courseRepository;
        this.courseApplyingRepository = courseApplyingRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
        this.timeTableRepository = timeTableRepository;
        this.courseSelectingRecordRepository = courseSelectingRecordRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public MyPage<Course> findAPageCourseForSelecting(Integer page, Integer size, String search, String stuNum,
                                                    SchoolYear schoolYear, Semester semester,
                                                    String classroomName, List<List<Integer>> selectTime) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        List<Course> temp = findAPageCourseIn(search, schoolYear, semester, classroomName, selectTime);
        List<Course> result = new ArrayList<>();
        for(Course course : temp){
            if(course.getCourseSelectType().equals(CourseSelectType.common)
                    || course.getMajorsOptional().contains(student.getMajor())){
                result.add(course);
            }
        }
        return MyPageTool.getPage(result, size, page);
//        String major = student.getMajor().getName();
//        Pageable pageable =  PageRequest.of(page - 1, size);
//        if(search.isEmpty()){
//            return courseRepository.findAllByMajor(major, pageable);
//        }
//        Course course = new Course();
//        course.setCourseName(search);
//        Major major1 = new Major();
//        major1.setName(search);
//        course.setMajor(major1);
//        ExampleMatcher matcher = ExampleMatcher.matchingAll()
//                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::exact)
//                .withIgnorePaths("courseId", "classPeriod", "creditHours", "credits", "capacity", "type", "teacherNum"
//                        , "courseNumber", "teacherName", "school", "classroom", "introduction", "courseStatus");
//        Example<Course> example = Example.of(course, matcher);
//        return courseRepository.findAll(example, pageable);
    }

    @Override
    public MyPage<Course> findAPageCourseOfTeacher(Integer page, Integer size, String jobNum, SchoolYear schoolYear, Semester semester) throws Exception {
        Teacher teacher = teacherRepository.findByJobNumber(jobNum);
        if(teacher == null) {
            throw new Exception("教师不存在");
        }
        List<Course> courses = courseRepository.findByTeacherNumAndSchoolYearAndSemester(jobNum, schoolYear, semester);
        return MyPageTool.getPage(courses, size, page);
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
    public MyPage<Course> findAPageCourse(Integer page, Integer size, String search, SchoolYear schoolYear, Semester semester
                                        , String classroomName, List<List<Integer>> selectTime){
        List<Course> result = findAPageCourseIn(search, schoolYear, semester, classroomName, selectTime);
        return MyPageTool.getPage(result, size, page);
//        Pageable pageable =  PageRequest.of(page - 1, size);
//        if(search.isEmpty()){
//            return courseRepository.findAll(pageable);
//        }
//        Course course = new Course();
//        course.setCourseName(search);
//        course.setTeacherNum(search);
//        course.setCourseNumber(search);
//        course.setIntroduction(search);
//        Major major = new Major();
//        course.setMajor(major);
//        course.setTeacherName(search);
//        School school = new School();
//        school.setName(search);
//        course.setSchool(school);
//        ExampleMatcher matcher = ExampleMatcher.matchingAny()
//                .withMatcher("courseName", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("courseNumber", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("teacherName", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("introduction", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withMatcher("teacherName", ExampleMatcher.GenericPropertyMatcher::contains)
//                .withIgnorePaths("id", "classPeriod", "creditHours", "credits", "capacity", "type");
//        Example<Course> example = Example.of(course, matcher);
//        return courseRepository.findAll(example,pageable);
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
    public List<Student>  getStudentListOfOneCourse(Integer courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("课程不存在");
        }
        List<CourseSelectingRecord> courseSelectingRecords = courseSelectingRecordRepository.findByCourse(course);
        List<Student> result = new ArrayList<>();
        for(CourseSelectingRecord courseSelectingRecord : courseSelectingRecords){
            result.add(courseSelectingRecord.getStudent());
        }
        return result;
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
        Course course = courseRepository.findByCourseId(courseApplyingData.getId());
        if(!applyingType.equals(CourseApplyingType.Publish)){
            if(course == null){
                throw new Exception("所申请课程不存在");
            }
            CourseApplying oldCourseApplying = courseApplyingRepository.findByCourseId(courseApplyingData.getId());
            if(oldCourseApplying != null){
                throw new Exception("该课程已有申请正在审核");
            }
        }
        if(applyingType.equals(CourseApplyingType.Delete)){
            //此处有return
            CourseApplying courseApplying = new CourseApplying(courseRepository.findByCourseId(courseApplyingData.getId()));
            courseApplyingRepository.save(courseApplying);
            return courseApplying;
        }

        if(school == null){
            throw new Exception("申请对应课程所属学院不存在");
        }
        Major major = majorRepository.findByNameAndSchool(courseApplyingData.getMajor(),school);
        if(major == null){
            throw new Exception("申请对应课程所属学院下不存在此专业");
        }
        Teacher teacher = teacherRepository.findByJobNumber(courseApplyingData.getTeacherNum());
        if(teacher == null){
            throw new Exception("该教师不存在");
        }
        Classroom classroom = classroomRepository.findByName(courseApplyingData.getClassroom());
        //检查容量
        Integer capacity = courseApplyingData.getCapacity();
        inspectCapacity(capacity, course, classroom);

        FormatCheck.courseApplyingDataCheck(courseApplyingData);
        List<Major> majorsOptional = new ArrayList<>();
        for (String str : courseApplyingData.getMajorLimits()){
            majorsOptional.add(majorRepository.findByName(str));
        }
        String classTime = TimeTool.transSchedule(TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(),
                TimeTool.getSectionNum(classroom.getSchedule()), Global.COURSE_MAX));
        CourseApplying courseApplying = new CourseApplying((courseApplyingData), school, major, classroom, majorsOptional, classTime);

        courseApplying.setType(applyingType);

        teacher.getCoursesApplying().add(courseApplying);
        teacherRepository.save(teacher);
        return courseApplying;
    }

    @Override
    public Course insertCourse(CourseApplyingData courseApplyingData) throws Exception {
        FormatCheck.courseApplyingDataCheck(courseApplyingData);
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
        //检查容量
        inspectCapacity(courseApplyingData.getCapacity(), null, classroom);
        //此处检测时间冲突
        TimeTool.addTimeMatrix(
                this.getClassroomTime(courseApplyingData.getClassroom(),
                        EnumTool.transSchoolYear(courseApplyingData.getYear()),
                        EnumTool.transSemester(courseApplyingData.getSemester())),
                TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(), TimeTool.getSectionNum(classroom.getSchedule()), -1));
        List<Major> majorsOptional = new ArrayList<>();
        for (String str : courseApplyingData.getMajorLimits()){
            majorsOptional.add(majorRepository.findByName(str));
        }
        Course course = new Course(courseApplyingData, school, major, classroom, majorsOptional, teacher.getName());
        //系列课程检查
        inspectSeriesCourse(course);

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
            timeMatrix = TimeTool.addTimeMatrix(TimeTool.makeTimeMatrix(classroom.getSchedule()), classSchedule);
            classroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(classroom);
        }

        return newCourse;
    }

    private void insertCourse(CourseApplying courseApplying) throws Exception {
        Classroom classroom = classroomRepository.findByName(courseApplying.getClassroom().getName());
        //此处检测时间冲突
        TimeTool.addTimeMatrix(
                this.getClassroomTime(courseApplying.getClassroom().getName(),
                        courseApplying.getSchoolYear(),
                        courseApplying.getSemester()),
                TimeTool.makeTimeMatrix(courseApplying.getClassTime()));
        Teacher teacher = teacherRepository.findByJobNumber(courseApplying.getTeacherNum());
        Course course = new Course(courseApplying);
        //系列课程检查
        inspectSeriesCourse(course);

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
            classroom.setSchedule(TimeTool.transSchedule(
                    TimeTool.addTimeMatrix(TimeTool.makeTimeMatrix(classroom.getSchedule()),
                            TimeTool.makeTimeMatrix(newCourse.getClassTime()))
            ));
            classroomRepository.save(classroom);
        }
    }

    @Override
    public Course updateCourse(CourseApplyingData courseApplyingData) throws Exception {
        FormatCheck.courseApplyingDataCheck(courseApplyingData);
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
        //检查容量
        inspectCapacity(courseApplyingData.getCapacity(), course, newClassroom);

        YearSemesterPair yearAndSemester = TimeTool.getPresentYearAndSemester();
        List<List<Integer>> thisCourseSchedule = TimeTool.makeTimeMatrix(courseApplyingData.getOccupyTime(), this.getLastSection(), course.getCourseId());
        if((course.getSchoolYear() == EnumTool.transSchoolYear(yearAndSemester.getYear()))
                &&(course.getSemester() == EnumTool.transSemester(yearAndSemester.getSemester()))){
            List<List<Integer>> timeMatrix =  TimeTool.subTimeMatrix(TimeTool.makeTimeMatrix(oldClassroom.getSchedule()), course.getCourseId());
            oldClassroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            //此处检测新教室时间冲突
            timeMatrix = TimeTool.subTimeMatrix(TimeTool.makeTimeMatrix(newClassroom.getSchedule()), course.getCourseId());
            timeMatrix = TimeTool.addTimeMatrix(timeMatrix, thisCourseSchedule);
            newClassroom.setSchedule(TimeTool.transSchedule(timeMatrix));
            classroomRepository.save(oldClassroom);
            classroomRepository.save(newClassroom);
        } else {
            List<List<Integer>> otherTimeMatrix = TimeTool.subTimeMatrix(
                    this.getClassroomTime(courseApplyingData.getClassroom(),
                            EnumTool.transSchoolYear(courseApplyingData.getYear()),
                            EnumTool.transSemester(courseApplyingData.getSemester())),
                    course.getCourseId());
            //此处检测时间冲突
            TimeTool.addTimeMatrix(otherTimeMatrix, thisCourseSchedule);
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

        syncSeriesCourse(thisCourse);

        return thisCourse;
    }

    @Override
    public Course deleteCourse(Integer courseId) throws Exception {
        Course course = courseRepository.findByCourseId(courseId);
        if(course == null){
            throw new Exception("所删除课程不存在，或已被删除");
        }
        if(!course.getCourseStatus().equals(CourseStatus.Published)){
            throw new Exception("无法删除已经结课或正在开展的课程");
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
        for (Course course : allCourse) {
            scheduleList = TimeTool.addTimeMatrix(scheduleList, TimeTool.makeTimeMatrix(course.getClassTime()));
        }
        return scheduleList;
    }

    private Integer getLastSection(){
        return getSections(timeTableRepository);
    }


    private List<Course> findAPageCourseIn(String search, SchoolYear schoolYear, Semester semester
            , String classroomName, List<List<Integer>> selectTime){
        List<Course> allCourses = courseRepository.findBySchoolYearAndSemester(schoolYear, semester);
        List<CourseInMatching> result = new ArrayList<>();

        boolean isEmptyTime = true;
        if(!(selectTime == null)){
            for(List<Integer> i : selectTime){
                if (!i.isEmpty()) {
                    isEmptyTime = false;
                    break;
                }
            }
        }
        for(Course course : allCourses) {
            if (!(classroomName == null) && !classroomName.equals("") && !course.getClassroom().getName().equals(classroomName)) {
                continue;
            }
            List<List<Integer>> courseTimeMatrix = null;
            List<List<Integer>> selectTimeMatrix = null;
            if (!isEmptyTime) {
                selectTimeMatrix = TimeTool.makeTimeMatrix(selectTime, this.getLastSection(), Global.COURSE_MAX);
                courseTimeMatrix = TimeTool.makeTimeMatrix(course.getClassTime());
            }

            if (!isEmptyTime && !TimeTool.isContainTimeMatrix(courseTimeMatrix, selectTimeMatrix)) {
                continue;
            }
            CourseMatchItem courseMatchItem = approximateMatchingSearch(course, search);
            if (!search.isEmpty() && courseMatchItem.equals(CourseMatchItem.NONE)){
                continue;
            }
            result.add(new CourseInMatching(course, courseMatchItem));
        }

        sortCourseByRelevance(result, search);
        return toCourseList(result);
    }

    //根据课程代码、课程名称、教师模糊搜索课程
    private CourseMatchItem approximateMatchingSearch(Course course, String search){
        CourseMatchItem isMatching = CourseMatchItem.NONE;
        if(course.getCourseNumber().contains(search)){
            isMatching = CourseMatchItem.COURSE_NUMBER;
        }
        if(course.getCourseName().contains(search)){
            isMatching = CourseMatchItem.COURSE_NAME;
        }
        if(course.getTeacherName().contains(search)){
            isMatching = CourseMatchItem.TEACHER_NAME;
        }
        return isMatching;
    }

    //根据相关性对搜索结果排序（暂用插入排序）
    private void sortCourseByRelevance(List<CourseInMatching> allCourseInMatching, String search){
        for (int i = 1; i < allCourseInMatching.size(); i ++) {
            // 记录要插入的数据
            CourseInMatching temp = allCourseInMatching.get(i);
            // 从已经排序的序列最右边的开始比较，找到比其相关性大的
            int j = i;
            while (j > 0 && temp.moreRelevanceThan(allCourseInMatching.get(j - 1), search)) {
                allCourseInMatching.set(j, allCourseInMatching.get(j - 1)) ;
                j --;
            }
            // 存在比其相关性大的，插入
            if (j != i) {
                allCourseInMatching.set(j, temp);
            }
        }
    }

    private void inspectCapacity(Integer capacity, Course course, Classroom classroom) throws Exception {
        if(capacity.compareTo(classroom.getCapacity()) > 0){
            throw new Exception("容量应小于等于教室容量");
        }
        if(course != null){
            Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
            if((!round.getAuthorityValue().equals(Global.FIRST_COURSE_SELECTING_ROUND)) && (capacity.compareTo(course.getStudentsNum()) < 0)){
                throw new Exception("容量应大于等于已选学生人数");
            }
        }
    }

    private void inspectSeriesCourse(Course course) throws Exception {
        List<Course> courses = courseRepository.findByCourseNumberAndSchoolYearAndSemester
                (course.getCourseNumber(), course.getSchoolYear(), course.getSemester());
        if(!courses.isEmpty()){
            Course oneOfSeries = courses.get(0);
            if(course.getCredits().equals(oneOfSeries.getCredits())){
                throw new Exception("学分应与同系列课程相同");
            }
            if(course.getCourseName().equals(oneOfSeries.getCourseName())){
                throw new Exception("课程名应与同系列课程相同");
            }
        }
    }

    private void syncSeriesCourse(Course course){
        List<Course> courses = courseRepository.findByCourseNumberAndSchoolYearAndSemester
                (course.getCourseNumber(), course.getSchoolYear(), course.getSemester());
        if(!courses.isEmpty()){
            for(Course oneOfSeries : courses){
                oneOfSeries.setCredits(course.getCredits());
                oneOfSeries.setCourseName(course.getCourseName());
                courseRepository.save(oneOfSeries);
            }
        }
    }
}
