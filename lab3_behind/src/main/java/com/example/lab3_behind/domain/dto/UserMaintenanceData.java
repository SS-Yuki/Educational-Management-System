package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMaintenanceData {
    private String number;

    private String name;

    private String password;

    private String id_num;

    private String phone_num;

    private String email;

}
