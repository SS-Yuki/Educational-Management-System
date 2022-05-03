package com.example.lab3_behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullCoursePageSearch {
    Integer pageNum;
    Integer pageSize;
    String search;
    String year;
    String semester;
    String classroom;
    List<List<Integer>> selectTime;
}
