package com.example.lab3_behind.controller;


import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.vo.JwtUserData;
import com.example.lab3_behind.vo.LoginUserData;
import com.example.lab3_behind.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;

//    @RequestMapping("/login")
//    public Result login(@RequestBody LoginUserData user){
//        Map<String,Object> map = new HashMap<>();
//        try {
//            UserAccount userAccount = userAccountService.login(user);
//            JwtUserData jwtUserData = JwtUserData.accountToJwt(userAccount);
//            String token = JwtUtil.createToken(jwtUserData);
//            map.put("code",200);
//            map.put("msg","success");
//            map.put("token",token);
//        }catch (Exception e){
//            return Result.fail(620,e.getMessage());
//        }
//        return Result.succ(map);
//    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginUserData user){
        String token;
        try {
            UserAccount userAccount = userAccountService.login(user);
            JwtUserData jwtUserData = JwtUserData.accountToJwt(userAccount);
            token = JwtUtil.createToken(jwtUserData);

        }catch (Exception e){
            return Result.fail(620,e.getMessage());
        }
        return Result.succ(token);
    }

}
