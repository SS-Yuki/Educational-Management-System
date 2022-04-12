package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.service.impl.TeachingAffairsServiceImpl;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeachingAffairsService {
    List<String> findAllClassroom();
    List<String> findAllTeachingBuilding();
    TimeTable addClassTime(ClassTimeData classTimeData) throws Exception;
    TimeTable updateClassTime(ClassTimeData classTimeData) throws Exception;
    TimeTable deleteClassTime(String name) throws Exception;
    Page<TimeTable> getAPageTimeTable(Integer page, Integer size, String search);
    Page<TeachingBuilding> findAPageTeachingBuilding(Integer page, Integer size, String search);
    Page<Classroom> findAPageClassroom(Integer page, Integer size, String search);
    Classroom insertClassroom(ClassroomAddingData classroomData) throws Exception;
    //Classroom updateClassroom();
    Classroom deleteClassroom(String classroomName, String teachingBuildingName) throws Exception;
    TeachingBuilding insertTeachingBuilding(String teachingBuildingName) throws Exception;
    TeachingBuilding updateTeachingBuilding(String teachingBuildingOldName, String teachingBuildingNewName) throws Exception;
    TeachingBuilding deleteTeachingBuilding(String teachingBuildingName) throws Exception;
}
