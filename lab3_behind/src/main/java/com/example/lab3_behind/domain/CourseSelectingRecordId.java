package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
public class CourseSelectingRecordId implements Serializable {

    @Column(name = "course_id")
    private Integer course_id;

    @Column(name = "student_id")
    private Integer student_id;

    public CourseSelectingRecordId() {}

    public CourseSelectingRecordId(
            Integer course_id,
            Integer student_id) {
        this.course_id = course_id;
        this.student_id = student_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        CourseSelectingRecordId that = (CourseSelectingRecordId) o;
        return Objects.equals(course_id, that.course_id) &&
                Objects.equals(student_id, that.student_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course_id, student_id);
    }
}