package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.CourseNameString;
import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.repository.*;
import com.example.lab3_behind.utils.FormatCheck;
import com.example.lab3_behind.common.forDomain.StudentStatus;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import com.example.lab3_behind.service.StudentService;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.lab3_behind.service.impl.TeachingAffairsServiceImpl.getSections;

@Service
public class StudentServiceImpl implements StudentService {
    StudentRepository studentRepository;
    SchoolRepository schoolRepository;
    MajorRepository majorRepository;
    CourseRepository courseRepository;
    TimeTableRepository timeTableRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, SchoolRepository schoolRepository,
                              MajorRepository majorRepository, CourseRepository courseRepository,
                              TimeTableRepository timeTableRepository){
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.majorRepository = majorRepository;
        this.courseRepository = courseRepository;
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    public Page<Student> findAPageStudent(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return studentRepository.findAll(pageable);
        }
        Student student = new Student();
        student.setName(search);
        student.setEmail(search);
        student.setPhoneNum(search);
        Major major = new Major();
        major.setName(search);
        student.setMajor(major);
        School school = new School();
        school.setName(search);
        student.setSchool(school);
        student.setStuNumber(search);
        student.setIdNum(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("stuNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("email", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("phoneNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("major", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("school", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("idNum", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id", "userAccount", "status");
        Example<Student> example = Example.of(student, matcher);
        return studentRepository.findAll(example,pageable);
    }

    @Override
    public List<Course> findCourseInSemester(String stuNum, SchoolYear schoolYear, Semester semester) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNum);
        if(student == null){
            throw new Exception("学生不存在");
        }
        List<Course> result = new ArrayList<>();
        for(Course course : getCourses(student)){
            if(course.getSchoolYear().equals(schoolYear) && course.getSemester().equals(semester)){
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public List<List<CourseNameString>> getClassScheduleInSemester(String stuNum, SchoolYear schoolYear, Semester semester) throws Exception {
        return transCourseNameString(getSchedule(studentRepository, timeTableRepository, stuNum, schoolYear, semester));
    }


    @Override
    public Student insertStudent(UserEnteringData userData) throws Exception {
        if(studentRepository.findByStuNumber(userData.getNumber()) != null){
            throw new Exception("工号已注册");
        }
        if(studentRepository.findByIdNum(userData.getIdNum()) != null){
            throw new Exception("身份证号已注册");
        }
        School school = schoolRepository.findByName(userData.getSchool());
        if(school == null){
            throw new Exception("学院不存在");
        }
        if(majorRepository.findByNameAndSchool(userData.getMajor(),school) == null){
            throw new Exception("教师所属学院下不存在此专业");
        }
        Student student = new Student(userData, schoolRepository.findByName(userData.getSchool()), majorRepository.findByName(userData.getMajor()));
        FormatCheck.userEnteringDataCheck(userData);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student updateStudentInfo(RevisableDataForAdmin userData, String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setIdNum(userData.getIdNum());
        student.setEmail(userData.getEmail());
        student.setName(userData.getName());
        student.setPhoneNum(userData.getPhoneNum());
        student.setStatus(userData.getStuStatus());
        student.setGrade(userData.getGrade());
        student.setMajor(majorRepository.findByName(userData.getMajor()));
        student.setSchool(schoolRepository.findByName(userData.getSchool()));
        student.getUserAccount().setPassword(userData.getPassword());
        if(!userData.getStuStatus().equals(StudentStatus.Normal)){
            student.getUserAccount().setPermission("false");
        } else {
            student.getUserAccount().setPermission("true");
        }
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student maintainStudentInfo(RevisableDataForUser userData, String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        student.setEmail(userData.getEmail());
        student.setPhoneNum(userData.getPhoneNum());
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student getByStuNumber(String stuNumber) throws Exception {
        Student student = studentRepository.findByStuNumber(stuNumber);
        if(student == null){
            throw new Exception("该用户不存在");
        }
        return student;
    }

    private List<List<CourseNameString>> transCourseNameString(List<List<Integer>> timeMatrix){
        List<List<CourseNameString>> result = new ArrayList<>();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<CourseNameString> buff = new ArrayList<>();
            for(Integer courseId : timeMatrix.get(i)){
                Course course = courseRepository.findByCourseId(courseId);
                buff.add(new CourseNameString(course.getCourseName()));
            }
            result.add(buff);
        }
        return result;
    }

    static List<List<Integer>> getSchedule(StudentRepository studentRepository, TimeTableRepository timeTableRepository,
                                           String stuNum, SchoolYear schoolYear, Semester semester) throws Exception {
        List<List<Integer>> result = TimeTool.getEmptyTimeMatrix(getSections(timeTableRepository));
        for(Course course : getCourses(studentRepository.findByStuNumber(stuNum))){
            if(course.getSchoolYear().equals(schoolYear) && course.getSemester().equals(semester)){
                result = TimeTool.addTimeMatrix(result, TimeTool.makeTimeMatrix(course.getClassTime()));
            }
        }
        return result;
    }

    static List<Course> getCourses(Student student){
        List<Course> result = new ArrayList<>();
        for(CourseSelectingRecord record : student.getRecords()){
            result.add(record.getCourse());
        }
        return result;
    }
}
