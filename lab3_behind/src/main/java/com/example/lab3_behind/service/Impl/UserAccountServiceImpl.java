package com.example.lab3_behind.service.Impl;


import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.repository.UserAccountRepository;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.vo.LoginUserData;
import com.example.lab3_behind.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountRepository userAccountRepository;
    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository){
        this.userAccountRepository=userAccountRepository;
    }

    @Override
    public UserAccount login(LoginUserData user){
        return new UserAccount(1,"20302010","123456","student");
    }
}
