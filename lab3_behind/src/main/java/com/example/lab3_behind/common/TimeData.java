package com.example.lab3_behind.common;

import com.example.lab3_behind.domain.TimeTable;
import com.example.lab3_behind.domain.dto.ClassTimeData;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TimeData {
    private String timeName;
    private String startTime;
    private String endTime;

    public ClassTimeData toClassTimeData(){
        Integer section=0;
        if(timeName !=null && timeName.length()>=3){
            section = Integer.valueOf(timeName.substring(1,timeName.length()-1));
        }
        return new ClassTimeData(section,startTime,endTime);
    }

    public static TimeData getTimeDataType(TimeTable timeTable){
        return new TimeData("第"+timeTable.getSection().toString()+"节",timeTable.getStartTime(),timeTable.getEndTime());
    }


    public static  List<TimeData> getContents(List<TimeTable> timeTables){
        List<TimeData> timeDatas = new ArrayList<>();
        for(TimeTable timeTable:timeTables){
            timeDatas.add(TimeData.getTimeDataType(timeTable));
        }
        return timeDatas;
    }

}
