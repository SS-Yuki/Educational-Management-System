package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.Major;
import com.example.lab3_behind.domain.School;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MajorContents {
    private String schoolName;
    private String introduction;
    private String majorName;

    public static List<MajorContents> getContents(List<Major> majors){
        List<MajorContents> majorContents = new ArrayList<>();
        for(Major major:majors){
            MajorContents temp = new MajorContents();
            temp.setMajorName(major.getName());
            temp.setSchoolName(major.getSchool().getName());
            temp.setIntroduction(major.getIntroduction());
            majorContents.add(temp);
        }
        return majorContents;
    }
}
