package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.vo.LoginUserData;
import com.example.lab3_behind.vo.Result;
import org.springframework.stereotype.Service;


public interface UserAccountService {
    UserAccount login(LoginUserData user);
}
