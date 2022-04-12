package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Classroom;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClassroomData {
    private String classroomName;
    private String buildingName;

    public static List<ClassroomData> getContents(List<Classroom> classrooms){
        List<ClassroomData> classroomDatas = new ArrayList<>();
        for(Classroom classroom:classrooms){
            ClassroomData temp = new ClassroomData();
            temp.setClassroomName(classroom.getName());
            temp.setBuildingName(classroom.getTeachingBuilding().getName());
            classroomDatas.add(temp);
        }
        return classroomDatas;
    }
}
