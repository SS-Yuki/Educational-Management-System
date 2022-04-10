package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;

public interface TeacherService {
    Teacher insertTeacher(UserEnteringData userData) throws Exception;
    Teacher updateTeacherInfo(RevisableDataForAdmin userData, String jobNumber) throws Exception;
    Teacher maintainTeacherInfo(RevisableDataForUser userData, String jobNumber) throws Exception;
}
