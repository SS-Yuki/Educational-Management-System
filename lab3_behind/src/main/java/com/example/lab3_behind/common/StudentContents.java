package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentContents {
    private String name;
    private String password;
    private String number;
    private String idNum;
    private String phoneNum;
    private String email;
    private String stuStatus;
    private String school;
    private String major;

    public static List<StudentContents> getContents(List<Student> students){
        List<StudentContents> studentContents = new ArrayList<>();
        for(Student student:students){
            StudentContents temp = new StudentContents();
            temp.setName(student.getName());
            temp.setNumber(student.getStuNumber());
            temp.setIdNum(student.getIdNum());
            temp.setPhoneNum(student.getPhoneNum());
            temp.setEmail(student.getEmail());
            if(student.getStatus().equals(StudentStatus.Graduated)){
                temp.setStuStatus("Graduated");
            }
            else temp.setStuStatus("Normal");
            temp.setSchool(student.getSchool().getName());
            temp.setMajor(student.getMajor().getName());
            temp.setPassword(student.getUserAccount().getPassword());
            studentContents.add(temp);
        }
        return studentContents;
    }

}
