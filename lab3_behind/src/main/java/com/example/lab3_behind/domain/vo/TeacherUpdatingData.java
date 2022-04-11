package com.example.lab3_behind.domain.vo;

import com.example.lab3_behind.common.StudentStatus;
import com.example.lab3_behind.common.TeacherStatus;
import lombok.Data;

@Data
public class TeacherUpdatingData {
    private String jobNumber;
    private String name;

    private String password;

    private String idNum;

    private String phoneNum;

    private String email;

    private String teaStatus;

    private String school;

    private String major;
}
