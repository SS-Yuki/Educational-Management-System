package com.example.lab3_behind.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SemestersData {
    String defaultSemester;
    List<String> Semesters;
}
