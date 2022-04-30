package com.example.lab3_behind.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageSearchWithYearAndSemester {
    Integer pageNum;
    Integer pageSize;
    String search;
    String year;
    String semester;
}
