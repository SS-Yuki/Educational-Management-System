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

    public Time startTime() throws ParseException {
        return new Time(new SimpleDateFormat("HH:mm:ss").parse(this.startTime + ":00").getTime());
    }

    public Time endTime() throws ParseException {
        return new Time(new SimpleDateFormat("HH:mm:ss").parse(this.endTime + ":00").getTime());
    }
}
