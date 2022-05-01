package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.*;
import com.example.lab3_behind.domain.dto.*;
import com.example.lab3_behind.service.impl.TeachingAffairsServiceImpl;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TeachingAffairsService {
    List<List<Boolean>> getClassroomTime(String name) throws Exception;
    List<List<Boolean>> getClassroomTime(String name, Integer excludedCourse) throws Exception;
    List<List<Boolean>> getCourseTime(String name, Integer excludedCourse);
    List<String> findAllClassroom();
    List<YearAndSemestersData> getAllYearAndSemesters();
    List<String> findAllTeachingBuilding();
    List<BuildingAndClassroomsData> getAllBuildingAndClassrooms();
    TimeTable addClassTime(ClassTimeData classTimeData) throws Exception;
    TimeTable updateClassTime(ClassTimeData classTimeData) throws Exception;
    TimeTable deleteClassTime() throws Exception;
    List<TimeTable> findAllTimeTable();
    Page<TimeTable> findAPageTimeTable(Integer page, Integer size, String search);
    Page<TeachingBuilding> findAPageTeachingBuilding(Integer page, Integer size, String search);
    Page<Classroom> findAPageClassroom(Integer page, Integer size, String search);
    Classroom insertClassroom(ClassroomAddingData classroomData) throws Exception;
    Classroom updateClassroom(ClassroomUpdatingData classroomData) throws Exception;
    Classroom deleteClassroom(String classroomName, String teachingBuildingName) throws Exception;
    TeachingBuilding insertTeachingBuilding(String teachingBuildingName) throws Exception;
    TeachingBuilding updateTeachingBuilding(String teachingBuildingOldName, String teachingBuildingNewName) throws Exception;
    TeachingBuilding deleteTeachingBuilding(String teachingBuildingName) throws Exception;
}
