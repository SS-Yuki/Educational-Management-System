package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomAddingData {
    String classroomName;
    String teachingBuildingName;
    Integer capacity;
    String schedule;
}
