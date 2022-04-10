package com.example.lab3_behind.controller;


import com.example.lab3_behind.domain.UserAccount;
import com.example.lab3_behind.service.UserAccountService;
import com.example.lab3_behind.utils.JwtUtil;
import com.example.lab3_behind.utils.RedisUtil;
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


    @PostMapping("/login")
    public Result login(@RequestBody LoginUserData user){
        Map<String,String> map = new HashMap<>();
        try {
            UserAccount userAccount = userAccountService.login(user);
            JwtUserData jwtUserData = JwtUserData.accountToJwt(userAccount);
            map.put("number","20302010");
            map.put("token",JwtUtil.createToken(jwtUserData));
            map.put("initLogin","false");
        }catch (Exception e){
            return Result.fail(620,e.getMessage());
        }
        return Result.succ(map);
    }
    @PostMapping("/logout")
    public Result login(@RequestBody String token){
        Map<String,String> map = new HashMap<>();
        try {
            JwtUtil.verify(token);
            JwtUserData jwtUserData = JwtUtil.getToken(token);
            RedisUtil.delete(token);
            map.put("number","20302010");
        }catch (Exception e){
            return Result.fail(621,e.getMessage());
        }
        return Result.succ(map);
    }
}
