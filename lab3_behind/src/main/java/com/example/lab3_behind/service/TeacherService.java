package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Teacher;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import org.springframework.data.domain.Page;

public interface TeacherService {
    Page<Teacher> findAPageTeacher(Integer page, Integer size, String search);
    Teacher insertTeacher(UserEnteringData userData) throws Exception;
    Teacher updateTeacherInfo(RevisableDataForAdmin userData, String jobNumber) throws Exception;
    Teacher maintainTeacherInfo(RevisableDataForUser userData, String jobNumber) throws Exception;
    Teacher getByJobNumber(String jobNumber)throws Exception;
}
