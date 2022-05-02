package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.repository.ClassroomRepository;
import com.example.lab3_behind.repository.CourseRepository;
import com.example.lab3_behind.repository.TeachingBuildingRepository;
import com.example.lab3_behind.repository.TimeTableRepository;
import com.example.lab3_behind.service.TeachingAffairsService;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.TimeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeachingAffairsServiceImpl implements TeachingAffairsService {
    ClassroomRepository classroomRepository;
    TeachingBuildingRepository teachingBuildingRepository;
    TimeTableRepository timeTableRepository;
    CourseRepository courseRepository;
    @Autowired
    public TeachingAffairsServiceImpl(ClassroomRepository classroomRepository,
                                      TeachingBuildingRepository teachingBuildingRepository,
                                      TimeTableRepository timeTableRepository,CourseRepository courseRepository){
        this.classroomRepository = classroomRepository;
        this.teachingBuildingRepository = teachingBuildingRepository;
        this.timeTableRepository = timeTableRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<List<Boolean>> getClassroomTime(String name, SchoolYear schoolYear, Semester semester) throws Exception {
        Classroom classroom = classroomRepository.findByName(name);
        if(classroom == null){
            throw new Exception("教室不存在");
        }
        List<Course> allCourse = courseRepository.findByClassroomAndSchoolYearAndSemester(classroom, schoolYear, semester);
        List<List<Integer>> scheduleList = TimeTool.makeTimeMatrix(allCourse.get(0).getClassTime());
        for (int i = 1; i < allCourse.size(); i++){
            scheduleList = TimeTool.addTimeMatrix(scheduleList, TimeTool.makeTimeMatrix(allCourse.get(i).getClassTime()));
        }
        String schedule = TimeTool.transSchedule(scheduleList);
        return TimeTool.getBoolTime(schedule);
    }

    @Override
    public List<List<Boolean>> getClassroomTime(String name, Integer excludedCourse) throws Exception {
        Classroom classroom = classroomRepository.findByName(name);
        if(classroom == null){
            throw new Exception("教室不存在");
        }
        String schedule = classroom.getSchedule();
        return TimeTool.getBoolTime(TimeTool.subTimeMatrix(TimeTool.makeTimeMatrix(schedule), excludedCourse));
    }

    @Override
    public List<List<Boolean>> getCourseTimeInClassroom(String name, Integer courseId) throws Exception {
        Classroom classroom = classroomRepository.findByName(name);
        if(classroom == null){
            throw new Exception("教室不存在");
        }
        String schedule = classroom.getSchedule();
        return TimeTool.getBoolTime(TimeTool.extractTimeMatrix(TimeTool.makeTimeMatrix(schedule), courseId));
    }

    @Override
    public TimeTable addClassTime(ClassTimeData classTimeData) throws Exception {
        Integer last = getLastSection() + 1;
        TimeTable newTime = new TimeTable(null, last, classTimeData.getStartTime(), classTimeData.getEndTime());
        timeTableRepository.save(newTime);

        List<Classroom> allClassrooms = classroomRepository.findAll();
        for(Classroom classroom : allClassrooms){
            classroom.setSchedule(classroom.getSchedule() + "0-0-0-0-0-0-0\n");
            classroomRepository.save(classroom);
        }

        return newTime;
    }

    @Override
    public TimeTable updateClassTime(ClassTimeData classTimeData) throws Exception {
        TimeTable timePeriod = timeTableRepository.findBySection(classTimeData.getSection());
        if(timePeriod == null){
            throw new Exception("该节次不存在");
        }
        timePeriod.setStartTime(classTimeData.getStartTime());
        timePeriod.setEndTime(classTimeData.getEndTime());
        timeTableRepository.save(timePeriod);
        return timePeriod;
    }

    @Override
    public TimeTable deleteClassTime() throws Exception {
        List<TimeTable> timeTables = timeTableRepository.findAll();
        if(timeTables.isEmpty()){
            throw new Exception("当前已无课程节次时间安排，无法删除");
        }
        TimeTable last = timeTableRepository.findBySection(getLastSection());

        List<Classroom> allClassrooms = classroomRepository.findAll();
        for(Classroom classroom : allClassrooms){
            int index = 0;
            int index1 = 0;
            int index2 = classroom.getSchedule().indexOf("\n") + 1;
            while (index2 != 0){
                index = index1;
                //Todo :判断最后一行有没有课，有就不能删
                index1 = index2;
                index2 = classroom.getSchedule().indexOf("\n", index1) + 1;
            }
            classroom.setSchedule(classroom.getSchedule().substring(0,index));
            classroomRepository.save(classroom);
        }

        timeTableRepository.delete(last);
        return last;
    }

    @Override
    public List<TimeTable> findAllTimeTable() {
        return timeTableRepository.findAll();
    }

    @Override
    public Page<TimeTable> findAPageTimeTable(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return timeTableRepository.findAll(pageable);
        }
        TimeTable timeTable = new TimeTable();
        timeTable.setStartTime(search);
        timeTable.setEndTime(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("startTime", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("endTime", ExampleMatcher.GenericPropertyMatcher::contains);
        Example<TimeTable> example = Example.of(timeTable, matcher);
        return timeTableRepository.findAll(example,pageable);
    }

    @Override
    public List<String> findAllClassroom(){
        List<Classroom> classrooms = classroomRepository.findAll();
        List<String> classroomNames = new ArrayList<>();
        for(Classroom classroom : classrooms){
            classroomNames.add(classroom.getName());
        }
        return classroomNames;
    }

    @Override
    public List<YearAndSemestersData> getAllYearAndSemesters() {
        List<YearAndSemestersData> result = new ArrayList<>();
        for(SchoolYear schoolYear : SchoolYear.values()){
              YearAndSemestersData data = new YearAndSemestersData();
              data.setYear(EnumTool.transString(schoolYear));
              data.setSemesters(new ArrayList<>());
              for(Semester semester : Semester.values()){
                  data.getSemesters().add(EnumTool.transString(semester));
              }
              result.add(data);
        }
        return result;
    }

    @Override
    public Page<Classroom> findAPageClassroom(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return classroomRepository.findAll(pageable);
        }
        Classroom classroom = new Classroom();
        classroom.setName(search);
        TeachingBuilding teachingBuilding = new TeachingBuilding();
        classroom.setTeachingBuilding(teachingBuilding);
        classroom.getTeachingBuilding().setName(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("teachingBuilding", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("id");
        Example<Classroom> example = Example.of(classroom, matcher);
        return classroomRepository.findAll(example,pageable);
    }

    @Override
    public Classroom insertClassroom(ClassroomAddingData classroomData) throws Exception {
        if(classroomData.getCapacity().compareTo(0) <= 0){
            throw new Exception("教室容量有误，必须为正整数");
        }
        Classroom classroom = classroomRepository.findByName(classroomData.getClassroomName());
        if(classroom != null){
            throw new Exception("该教室名已存在");
        }
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(classroomData.getTeachingBuildingName());
        if(teachingBuilding == null){
            throw new Exception("教室信息有误，教学楼不存在");
        }
        String schedule = "";
        for(int i = 0; i < getLastSection().intValue(); i++){
            schedule = schedule + "0-0-0-0-0-0-0\n";
        }
        Classroom newClassroom = new Classroom(null, classroomData.getClassroomName(), teachingBuilding,
                classroomData.getCapacity(), schedule);
        teachingBuilding.getClassrooms().add(newClassroom);
        teachingBuildingRepository.save(teachingBuilding);
        return newClassroom;
    }

    @Override
    public Classroom updateClassroom(ClassroomUpdatingData classroomData) throws Exception {
        if(classroomData.getCapacity().compareTo(0) <= 0){
            throw new Exception("教室容量有误，必须为正整数");
        }
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(classroomData.getTeachingBuilding());
        if(teachingBuilding == null){
            throw new Exception("教室信息有误，教学楼不存在");
        }
        Classroom classroom = classroomRepository.findByName(classroomData.getOldClassroomName());
        Classroom thisClassroom = teachingBuilding.getClassrooms().get(teachingBuilding.getClassrooms().indexOf(classroom));
        thisClassroom.setName(classroomData.getNewClassroomName());
        thisClassroom.setCapacity(classroomData.getCapacity());
        thisClassroom.setSchedule(classroomData.getSchedule());
        teachingBuildingRepository.save(teachingBuilding);
        return thisClassroom;
    }

    @Override
    public Classroom deleteClassroom(String classroomName, String teachingBuildingName) throws Exception {
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(teachingBuildingName);
        if(teachingBuilding == null){
            throw new Exception("教室信息有误，教学楼不存在");
        }
        Classroom classroom = classroomRepository.findByName(classroomName);
        if(classroom == null){
            throw new Exception("该教室不存在");
        }
        Classroom thisClassroom = teachingBuilding.getClassrooms().get(teachingBuilding.getClassrooms().indexOf(classroom));
        teachingBuilding.getClassrooms().remove(thisClassroom);
        classroomRepository.delete(thisClassroom);
        teachingBuildingRepository.save(teachingBuilding);
        return thisClassroom;
    }

    @Override
    public List<String> findAllTeachingBuilding(){
        List<TeachingBuilding> buildings = teachingBuildingRepository.findAll();
        List<String> buildingName = new ArrayList<>();
        for(TeachingBuilding building : buildings){
            buildingName.add(building.getName());
        }
        return buildingName;
    }
    @Override
    public Page<TeachingBuilding> findAPageTeachingBuilding(Integer page, Integer size, String search){
        Pageable pageable =  PageRequest.of(page - 1, size);
        if(search.isEmpty()){
            return teachingBuildingRepository.findAll(pageable);
        }
        TeachingBuilding teachingBuilding = new TeachingBuilding();
        teachingBuilding.setName(search);
        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withIgnoreCase(true)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnorePaths("classrooms");
        Example<TeachingBuilding> example = Example.of(teachingBuilding, matcher);
        return teachingBuildingRepository.findAll(example,pageable);
    }

    @Override
    public TeachingBuilding insertTeachingBuilding(String teachingBuildingName) throws Exception {
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(teachingBuildingName);
        if(teachingBuilding != null){
            throw new Exception("已有该名称的教学楼");
        }
        TeachingBuilding newBuilding = new TeachingBuilding();
        newBuilding.setName(teachingBuildingName);
        teachingBuildingRepository.save(newBuilding);
        return newBuilding;
    }

    @Override
    public TeachingBuilding updateTeachingBuilding(String teachingBuildingOldName, String teachingBuildingNewName) throws Exception {
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(teachingBuildingOldName);
        if(teachingBuilding == null){
            throw new Exception("该教学楼不存在");
        }
        teachingBuilding.setName(teachingBuildingNewName);
        teachingBuildingRepository.save(teachingBuilding);
        return teachingBuilding;
    }

    @Override
    public TeachingBuilding deleteTeachingBuilding(String teachingBuildingName) throws Exception {
        TeachingBuilding teachingBuilding = teachingBuildingRepository.findByName(teachingBuildingName);
        if(teachingBuilding == null){
            throw new Exception("该教学楼不存在");
        }
        teachingBuildingRepository.delete(teachingBuilding);
        return teachingBuilding;
    }

    @Override
    public List<BuildingAndClassroomsData> getAllBuildingAndClassrooms(){
        List<BuildingAndClassroomsData> result = new ArrayList<>();
        List<TeachingBuilding> buildings = teachingBuildingRepository.findAll();
        for(TeachingBuilding building : buildings){
            List<String> classroomNames = new ArrayList<>();
            for(Classroom classroom : building.getClassrooms()){
                classroomNames.add(classroom.getName());
            }
            result.add(new BuildingAndClassroomsData(building.getName(),classroomNames));
        }
        return result;
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
