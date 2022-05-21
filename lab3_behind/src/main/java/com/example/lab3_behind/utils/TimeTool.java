package com.example.lab3_behind.utils;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.domain.dto.YearSemesterPair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class TimeTool {

    public static YearSemesterPair getPresentYearAndSemester(){
        YearSemesterPair result = new YearSemesterPair();
        Calendar cal=Calendar.getInstance();
        if((cal.get(Calendar.MONTH)*100 + cal.get(Calendar.DATE)) >
                (Global.FIRST_SEMESTER_START_TIME.getFirst()*100 + Global.SECOND_SEMESTER_START_TIME.getSecond())){
            result.setYear(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.YEAR) + 1));
            result.setSemester("第1学期");
        } else{
            result.setYear((cal.get(Calendar.YEAR) - 1) + "-" + (cal.get(Calendar.YEAR)));
            if((cal.get(Calendar.MONTH)*100 + cal.get(Calendar.DATE)) <
                    (Global.FIRST_SEMESTER_START_TIME.getFirst()*100 + Global.SECOND_SEMESTER_START_TIME.getSecond())) {
                result.setSemester("第2学期");
            } else {
              result.setSemester("第1学期");
            }
        }
        return result;
    }

    public static List<List<Integer>> getEmptyTimeMatrix(Integer sections){
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < Global.WEEKDAY; i ++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < sections; j ++){
                temp.add(0);
            }
            result.add(temp);
        }
        return result;
    }

    public static List<List<Boolean>> getBoolTime(String schedule){
        List<List<Boolean>> result = new ArrayList<>();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Boolean> buff = new ArrayList<>();
            result.add(buff);
        }
        int index1 = 0;
        int index2 = schedule.indexOf("\n") + 1;
        while (index2 != 0){
            String section = schedule.substring(index1, index2 - 1);
            String[] sectionArr = section.split("-");
            for(int i = 0; i < result.size(); i++){
                result.get(i).add(Integer.parseInt(sectionArr[i]) != Global.CLASSROOM_TIME_SPARE);
            }
            index1 = index2;
            index2 = schedule.indexOf("\n", index1) + 1;
        }
        return result;
    }

    public static List<List<Boolean>> getBoolTime(List<List<Integer>> timeMatrix){
        List<List<Boolean>> result = new ArrayList<>();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Boolean> buff = new ArrayList<>();
            for(Integer course : timeMatrix.get(i)){
                buff.add(!course.equals(Global.CLASSROOM_TIME_SPARE));
            }
            result.add(buff);
        }
        return result;
    }

    public static List<List<Integer>> transMaxInSchedule(String schedule, Integer courseId){
        List<List<Integer>> result = makeTimeMatrix(schedule);
        for(int i = 0; i < Global.WEEKDAY; i++){
            for(int j = 0; j < getSectionNum(schedule); j ++){
                if(result.get(i).get(j).equals(Global.COURSE_MAX)){
                    result.get(i).set(j, courseId);
                }
            }
        }
        return result;
    }


    public static List<List<Integer>> makeTimeMatrix(String schedule){
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < Global.WEEKDAY; i++){
            result.add(new ArrayList<>());
        }
        int index1 = 0;
        int index2 = schedule.indexOf("\n") + 1;
        while (index2 != 0){
            String section = schedule.substring(index1, index2 - 1);
            String[] sectionArr = section.split("-");
            for(int i = 0; i < Global.WEEKDAY; i++){
                result.get(i).add(Integer.valueOf(sectionArr[i]));
            }
            index1 = index2;
            index2 = schedule.indexOf("\n", index1) + 1;
        }
        return result;
    }

    public static List<List<Integer>> makeTimeMatrix(List<List<Integer>> occupyTime, Integer sections, Integer courseId){
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Integer> temp = new ArrayList<>();
            for(int section = 0; section < sections; section ++){
                temp.add(Global.CLASSROOM_TIME_SPARE);
            }
            for(Integer j : occupyTime.get(i)){
                temp.set(j-1,courseId);
            }
            result.add(temp);
        }
        return result;
    }

    public static List<List<Integer>> addTimeMatrix(List<List<Integer>> a, List<List<Integer>> b) throws Exception {
        List<List<Integer>> result = new ArrayList<>();
        int sections = a.get(0).size();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < sections; j ++){
                int a1 = a.get(i).get(j), b1 = b.get(i).get(j);
                if((a1 != Global.CLASSROOM_TIME_SPARE) && (b1 != Global.CLASSROOM_TIME_SPARE)){
                    throw new Exception("时间冲突");
                } else {
                    temp.add(a.get(i).get(j) + b.get(i).get(j));
                }
            }
            result.add(temp);
        }
        return result;
    }

    public static List<List<Integer>> subTimeMatrix(List<List<Integer>> a, Integer courseId) {
        List<List<Integer>> result = new ArrayList<>();
        int sections = a.get(0).size();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < sections; j ++){
                temp.add(Objects.equals(a.get(i).get(j), courseId) ? Global.CLASSROOM_TIME_SPARE : a.get(i).get(j));
            }
            result.add(temp);
        }
        return result;
    }

    public static List<List<Integer>> extractTimeMatrix(List<List<Integer>> a, Integer courseId) {
        List<List<Integer>> result = new ArrayList<>();
        int sections = a.get(0).size();
        for(int i = 0; i < Global.WEEKDAY; i++){
            List<Integer> temp = new ArrayList<>();
            for(int j = 0; j < sections; j ++){
                temp.add(Objects.equals(a.get(i).get(j), courseId) ? courseId : Global.CLASSROOM_TIME_SPARE);
            }
            result.add(temp);
        }
        return result;
    }

    public static boolean isContainTimeMatrix(List<List<Integer>> a, List<List<Integer>> b){
        boolean isContain = true;
        int sections = a.get(0).size();
        for(int i = 0; i < Global.WEEKDAY; i++){
            for(int j = 0; j < sections; j ++){
                if ((b.get(i).get(j).equals(Global.COURSE_MAX) && a.get(i).get(j).equals(Global.CLASSROOM_TIME_SPARE))) {
                    isContain = false;
                    break;
                }
            }
        }
        return isContain;
    }

    public static String transSchedule(List<List<Integer>> timeMatrix){
        String result = "";
        int sectionNum = timeMatrix.get(0).size();
        for(int i = 0; i < sectionNum; i++){
            for(int j = 0; j < Global.WEEKDAY; j++){
                result = result + timeMatrix.get(j).get(i);
                if((j + 1) == Global.WEEKDAY){
                    result = result + "\n";
                } else {
                    result = result + "-";
                }
            }
        }
        return result;
    }

    public static Integer getSectionNum(String schedule){
        int result = 0;
        int index1;
        int index2 = schedule.indexOf("\n") + 1;
        while (index2 != 0){
            index1 = index2;
            result ++;
            index2 = schedule.indexOf("\n", index1) + 1;
        }
        return result;
    }
}
