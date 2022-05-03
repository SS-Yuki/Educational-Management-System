package com.example.lab3_behind.common;

import org.springframework.data.util.Pair;

public class Global {
    public static final int STU_NUMBER_LENGTH = 6;
    public static final int JOB_NUMBER_LENGTH = 8;
    public static final int CLASSROOM_TIME_SPARE = 0;
    public static final int WEEKDAY = 7;
    public static final Pair<Integer, Integer> FIRST_SEMESTER_START_TIME = Pair.of(8,20);
    public static final Pair<Integer, Integer> SECOND_SEMESTER_START_TIME = Pair.of(2,20);
    public static final int COURSE_MAX = 9999999;
    public static final String NOT_IN_COURSE_SELECTING_ROUND = "0";
    public static final String FIRST_COURSE_SELECTING_ROUND = "1";
    public static final String LAST_COURSE_SELECTING_ROUND = "2";
}
