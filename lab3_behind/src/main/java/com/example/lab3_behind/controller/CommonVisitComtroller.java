package com.example.lab3_behind.controller;

import com.example.lab3_behind.common.SemestersData;
import com.example.lab3_behind.domain.resp.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("common")
public class CommonVisitComtroller {

    @RequestMapping("allSemesters")
    public Result allSemesters(){
        String s1 = "2020-2021春";
        String s2 = "2020-2021秋";
        String s3 = "2021-2022春";
        String s4 = "2021-2022秋";
        List<String>  semesters = new ArrayList<>();
        semesters.add(s1);
        semesters.add(s2);
        semesters.add(s2);
        semesters.add(s4);
        return Result.succ(new SemestersData(s4,semesters));
    }
}
