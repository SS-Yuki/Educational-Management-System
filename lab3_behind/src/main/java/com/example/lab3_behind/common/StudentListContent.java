package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.utils.EnumTool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentListContent {
    String stuNumber;
    String name;
    String grade;
    String major;


    public static List<StudentListContent> getContents(List<Student> students){
        List<StudentListContent> studentListContents = new ArrayList<>();
        for(Student student:students){
            StudentListContent temp = new StudentListContent(
                    student.getStuNumber(),
                    student.getName(),
                    EnumTool.transString(student.getGrade()),
                    student.getMajor().getName());
            studentListContents.add(temp);
        }
        return studentListContents;
    }
}
