package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableUserData;
import com.example.lab3_behind.domain.dto.UserEnteringData;

public interface TeacherService {
    Teacher insertTeacher(UserEnteringData userData) throws Exception;
    Teacher updateTeacherInfo(RevisableUserData userData);
}
