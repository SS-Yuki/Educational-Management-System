package com.example.lab3_behind.service;

public interface AuthorityService {
    Boolean checkCourseSelectingAuthority();
    Boolean changeCourseSelectingAuthority(boolean status);
}