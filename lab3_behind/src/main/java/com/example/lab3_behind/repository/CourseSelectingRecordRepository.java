package com.example.lab3_behind.repository;

import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseSelectingRecord;
import com.example.lab3_behind.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseSelectingRecordRepository extends JpaRepository<CourseSelectingRecord, Long> {
    List<CourseSelectingRecord> findByStudent(Student student);
    CourseSelectingRecord findByStudentAndCourse(Student student, Course course);
}
