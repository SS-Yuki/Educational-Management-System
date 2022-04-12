package com.example.lab3_behind.common;

import lombok.Data;

@Data
public class ChangePasswordData {
    private String number;
    private String oldPassword;
    private String newPassword;
}
