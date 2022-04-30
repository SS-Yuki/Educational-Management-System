package com.example.lab3_behind.controller;

import com.example.lab3_behind.domain.dto.YearAndSemestersData;
import com.example.lab3_behind.domain.resp.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonVisitComtroller {

    @RequestMapping("allSemesters")
    public Result allSemesters(){
        Map<String,Object> map = new HashMap<>();
        map.put("defaultYear","2021-2022");
        map.put("defaultSemester","春季学期");
        List<String> semesters = new ArrayList<>();
        semesters.add("春季学期");
        semesters.add("秋季学期");
        YearAndSemestersData yearAndSemestersData = new YearAndSemestersData("2021-2022",semesters);
        List<YearAndSemestersData> yearAndSemestersDatas = new ArrayList<>();
        yearAndSemestersDatas.add(yearAndSemestersData);
        map.put("yearAndSemesters",yearAndSemestersDatas);

        return Result.succ(map);
    }
}
