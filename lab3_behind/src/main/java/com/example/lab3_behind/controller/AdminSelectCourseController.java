package com.example.lab3_behind.controller;

import com.example.lab3_behind.domain.resp.Result;
import com.example.lab3_behind.service.AuthorityService;
import com.example.lab3_behind.service.CourseSelectingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminSelectCourseController {
    @Autowired
    AuthorityService authorityService;
    @Autowired
    CourseSelectingService courseSelectingService;

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

    @RequestMapping("/whichTurn")
    public Result whichTurn(){
        try {
            String ans;
            Integer round = authorityService.getPresentCourseSelectingRound();
            if(round == 1)ans = "一轮选课";
            else if(round == 2)ans = "二轮选课";
            else ans = "无法选课轮次";
            return Result.succ(ans);
        }catch (Exception e){
            //e.printStackTrace();
            return Result.fail(841,e.getMessage());
        }
    }

    @RequestMapping("/nextTurn")
    public Result nextTurn(){
        try {
            authorityService.toNextCourseSelectingRound();
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(841,e.getMessage());
        }
    }

    @RequestMapping("/randomSelect")
    public Result randomSelect(){
        try {
            courseSelectingService.RandomSelectFirstRound();
            return Result.succ(null,"随机筛选完成！");
        }catch (Exception e){
            return Result.fail(841,e.getMessage());
        }
    }

    @RequestMapping("/startThisSemesterSelectCourse")
    public Result startThisSemesterSelectCourse(){
        try {
            authorityService.courseSelectingStart();
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(841,e.getMessage());
        }
    }

    @RequestMapping("/endThisSemesterSelectCourse")
    public Result endThisSemesterSelectCourse(){
        try {
            authorityService.courseSelectingEnd();
            return Result.succ(null);
        }catch (Exception e){
            return Result.fail(841,e.getMessage());
        }
    }


}
