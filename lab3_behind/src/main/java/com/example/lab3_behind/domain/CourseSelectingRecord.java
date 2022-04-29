package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_selecting_record")
public class CourseSelectingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "course_id")
    private String courseId;

    @Column(name = "student_num")
    private String studentNum;

    @Column(name = "score")
    private double score;

}
