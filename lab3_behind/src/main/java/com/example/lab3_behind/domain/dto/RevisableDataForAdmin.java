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

    private String id_num;

    private String phone_num;

    private String email;

    private StudentStatus stu_status;

    private TeacherStatus tea_status;

    private String school;

    private String major;

}
