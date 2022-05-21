package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_table")
public class TimeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "section", unique = true)
    private Integer section;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    public Time startTime() throws ParseException {
        return new Time(new SimpleDateFormat("HH:mm:ss").parse(this.startTime + ":00").getTime());
    }

    public Time endTime() throws ParseException {
        return new Time(new SimpleDateFormat("HH:mm:ss").parse(this.endTime + ":00").getTime());
    }
}
