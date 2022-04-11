package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEnteringData {
    private String number;

    private String name;

    private String idNum;

    private String phoneNum;

    private String email;

    private String role;

    private String school;

    private String major;



}
