package com.example.lab3_behind.service;

public interface AuthorityService {
    Boolean checkCourseSelectingAuthority();
    Integer getPresentCourseSelectingRound();
    void toNextCourseSelectingRound() throws Exception;
    void courseSelectingStart() throws Exception;
    void courseSelectingEnd() throws Exception;
    void changeCourseSelectingAuthority(boolean status) throws Exception;
}
