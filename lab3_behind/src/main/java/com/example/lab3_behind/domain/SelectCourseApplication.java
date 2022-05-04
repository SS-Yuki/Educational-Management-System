package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.forDomain.Grade;
import com.example.lab3_behind.common.forDomain.SelectCourseApplicationStatus;
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

    @Lob
    @Column(name = "feed_back", columnDefinition="TEXT")
    private String feedBack;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SelectCourseApplicationStatus status;

}
