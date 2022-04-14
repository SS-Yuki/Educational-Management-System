package com.example.lab3_behind.controller;

import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminSelectCourseController {
    @Autowired
    AuthorityService authorityService;

    @RequestMapping("/isSelectCourseOpen")
    public Result isSelectCourseOpen(){
        String open;
        if(authorityService.checkCourseSelectingAuthority()){
            open = "开放";
        }
        else{
            open = "关闭";
        }
        return Result.succ(open);
    }
    @RequestMapping("/openSelectCourse")
    public Result openSelectCourse(){
        try {
            authorityService.changeCourseSelectingAuthority(true);
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(840,e.getMessage());
        }
    }

    @RequestMapping("/closeSelectCourse")
    public Result closeSelectCourse(){
        try {
            authorityService.changeCourseSelectingAuthority(false);
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(841,e.getMessage());
        }
    }
}
