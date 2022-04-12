package com.example.lab3_behind.common;


import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.TeachingBuilding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingContents {
    private String buildingName;

    public static List<BuildingContents> getContents(List<TeachingBuilding> teachingBuildings){
        List<BuildingContents> buildingContents = new ArrayList<>();
        for(TeachingBuilding teachingBuilding:teachingBuildings){
            BuildingContents temp = new BuildingContents(teachingBuilding.getName());
            buildingContents.add(temp);
        }
        return buildingContents;
    }
}
