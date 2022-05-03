package com.example.lab3_behind.service;

public interface AuthorityService {
    Boolean checkCourseSelectingAuthority();
    Integer getPresentCourseSelectingRound();
    void toNextCourseSelectingRound() throws Exception;
    void courseSelectingStart();
    void courseSelectingEnd();
    void changeCourseSelectingAuthority(boolean status) throws Exception;
}
