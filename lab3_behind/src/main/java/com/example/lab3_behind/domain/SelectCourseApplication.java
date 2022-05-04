package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SelectCourseApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stu_number")
    private String stuNumber;

    @Column(name = "course_id")
    private Integer courseId;

    @Lob
    @Column(name = "reason", columnDefinition="TEXT")
    private String reason;
}
