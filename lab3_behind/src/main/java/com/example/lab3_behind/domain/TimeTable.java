package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_table")
public class TimeTable {
    @Id
    @Column(name = "num", unique = true)
    Integer num;

    @Column(name = "start_time")
    String startTime;

    @Column(name = "end_time")
    String endTime;
}
