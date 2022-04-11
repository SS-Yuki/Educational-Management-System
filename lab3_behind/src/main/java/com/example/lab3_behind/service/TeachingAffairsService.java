package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeachingAffairsService {
    List<Classroom> findAllClassroom();
    List<TimeTable> getTimeTable();
    TimeTable addClassTime(ClassTimeData classTimeData);
    TimeTable updateClassTime(ClassTimeData classTimeData);
    TimeTable deleteClassTime();
    Page<TeachingBuilding> findAPageTeachingBuilding(Integer page, Integer size, String search);
    Page<Classroom> findAPageClassroom(Integer page, Integer size, String search);
    Classroom insertClassroom(String classroomName);
    //Classroom updateClassroom();
    Classroom deleteClassroom(String classroomName);
    TeachingBuilding insertTeachingBuilding(String classroomName);
    //TeachingBuilding updateTeachingBuilding();
    TeachingBuilding deleteTeachingBuilding(String teachingBuildingName);
}
