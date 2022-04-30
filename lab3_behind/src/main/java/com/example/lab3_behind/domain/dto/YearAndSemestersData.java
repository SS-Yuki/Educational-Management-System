package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearAndSemestersData {
    String year;
    List<String> semesters;
}
