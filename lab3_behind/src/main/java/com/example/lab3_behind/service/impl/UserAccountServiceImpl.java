package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.repository.UserAccountRepository;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.domain.dto.LoginUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    UserAccountRepository userAccountRepository;
    @Autowired
    public UserAccountServiceImpl(UserAccountRepository userAccountRepository){
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount login(LoginUserData user) throws Exception {
        UserAccount userAccount = userAccountRepository.findByAccount(user.getNumber());
        if(userAccount==null){
            throw new Exception("账号不存在");
        }
        if(!userAccount.getPassword().equals(user.getPassword())){
            throw new Exception("密码错误");
        }
        if(userAccount.getPermission().equals("false")){
            throw new Exception("账号目前无权限登录");
        }
        return userAccount;
    }
}
