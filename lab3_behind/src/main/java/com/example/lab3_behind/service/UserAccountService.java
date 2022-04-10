package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.domain.dto.LoginUserData;


public interface UserAccountService {
    UserAccount login(LoginUserData user) throws Exception;
}
