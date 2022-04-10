package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserData {
    String number;
    String password;
}
