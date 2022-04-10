package com.example.lab3_behind.domain.dto;

import com.example.lab3_behind.common.StudentStatus;
import com.example.lab3_behind.common.TeacherStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevisableDataForAdmin {
    private String name;

    private String password;

    private String idNum;

    private String phoneNum;

    private String email;

    private StudentStatus stuStatus;

    private TeacherStatus teaStatus;

    private String school;

    private String major;

}
