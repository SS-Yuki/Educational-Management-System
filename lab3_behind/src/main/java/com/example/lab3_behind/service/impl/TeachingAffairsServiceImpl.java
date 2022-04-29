package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.domain.Classroom;
import com.example.lab3_behind.domain.TeachingBuilding;
import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.BuildingAndClassroomsData;
import com.example.lab3_behind.domain.dto.ClassTimeData;
import com.example.lab3_behind.domain.dto.ClassroomAddingData;
import com.example.lab3_behind.domain.dto.ClassroomUpdatingData;
import com.example.lab3_behind.repository.ClassroomRepository;
import com.example.lab3_behind.repository.TeachingBuildingRepository;
import com.example.lab3_behind.repository.TimeTableRepository;
import com.example.lab3_behind.service.TeachingAffairsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeachingAffairsServiceImpl implements TeachingAffairsService {
    ClassroomRepository classroomRepository;
    TeachingBuildingRepository teachingBuildingRepository;
    TimeTableRepository timeTableRepository;
    @Autowired
    public TeachingAffairsServiceImpl(ClassroomRepository classroomRepository,
                                      TeachingBuildingRepository teachingBuildingRepository,
                                      TimeTableRepository timeTableRepository){
        this.classroomRepository = classroomRepository;
        this.teachingBuildingRepository = teachingBuildingRepository;
        this.timeTableRepository = timeTableRepository;
    }

    @Override
    public TimeTable addClassTime(ClassTimeData classTimeData) throws Exception {
        Integer last = 0;
        List<TimeTable> timeTables = timeTableRepository.findAll();
        for(TimeTable timeTable : timeTables){
            Integer temp = timeTable.getSection();
            if(temp.compareTo(last) >= 0){
                last = temp;
            }
        }
        last = last + 1;
        TimeTable newTime = new TimeTable(null, last, classTimeData.getStartTime(), classTimeData.getEndTime());
        timeTableRepository.save(newTime);
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
        if(timeTables == null){
            throw new Exception("当前已无课程节次时间安排，无法删除");
        }
        TimeTable last = new TimeTable(null, 0, null, null);
        for(TimeTable timeTable : timeTables){
            if(timeTable.getSection().compareTo(last.getSection()) >= 0){
                last = timeTable;
            }
        }
        timeTableRepository.delete(last);
        return last;
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
        Classroom newClassroom = new Classroom(null, classroomData.getClassroomName(),teachingBuilding, classroomData.getCapacity());
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
}
