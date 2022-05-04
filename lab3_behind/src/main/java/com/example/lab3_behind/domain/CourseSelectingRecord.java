package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.forDomain.CourseSelectType;
import com.example.lab3_behind.common.forDomain.StudyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_selecting_record")
public class CourseSelectingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "score")
    private double score;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_status")
    private StudyStatus studyStatus;
//    @EmbeddedId
//    private CourseSelectingRecordId id;
//
//    @ManyToOne
//    @MapsId("course_id")
//    private Course course;
//
//    @ManyToOne
//    @MapsId("student_id")
//    private Student student;
//
//    @Column(name = "score")
//    private double score;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "study_status")
//    private StudyStatus studyStatus;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//
//        if (o == null || getClass() != o.getClass())
//            return false;
//
//        CourseSelectingRecord that = (CourseSelectingRecord) o;
//        return Objects.equals(course, that.course) &&
//                Objects.equals(student, that.student);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(course, student);
//    }
}
