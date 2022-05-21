package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassTimeData {
    Integer section;

    String startTime;

    String endTime;


}
