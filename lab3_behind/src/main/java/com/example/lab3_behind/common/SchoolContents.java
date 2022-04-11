package com.example.lab3_behind.common;


import com.example.lab3_behind.domain.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolContents {
    private String schoolName;
    private String introduction;

    public static List<SchoolContents> getContents(List<School> schools){
        List<SchoolContents> schoolContents = new ArrayList<>();
        for(School school:schools){
            SchoolContents temp = new SchoolContents();
            temp.setSchoolName(school.getName());
            temp.setIntroduction(school.getIntroduction());
            schoolContents.add(temp);
        }
        return schoolContents;
    }
}
