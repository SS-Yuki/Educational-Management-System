package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassroomUpdatingData {
    String oldClassroomName;

    String newClassroomName;

    String TeachingBuilding;

    Integer capacity;

    String schedule;
}
