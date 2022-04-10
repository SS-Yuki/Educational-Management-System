package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer course_id;

    @Column(name = "course_number", length = 36, nullable = true)
    private String courseNumber;

    @Column(name = "course_name", length = 256, nullable = true)
    private String courseName;

    @Column(name = "department ", length = 36, nullable = true)
    private String department ;

    @Column(name = "class_period")
    private String classPeriod ;

    @Column(name = "credit_hours", nullable = true)
    private Integer creditHours;

    @Column(name = "credits", nullable = true)
    private Integer credits;

    @Column(name = "capacity", nullable = true)
    private Integer capacity;

    @Lob
    @Column(name = "introduction", columnDefinition="TEXT")
    private String introduction;
}
