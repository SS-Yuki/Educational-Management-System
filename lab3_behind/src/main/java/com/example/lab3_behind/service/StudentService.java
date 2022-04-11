package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.RevisableDataForAdmin;
import com.example.lab3_behind.domain.dto.RevisableDataForUser;
import com.example.lab3_behind.domain.dto.UserEnteringData;

public interface StudentService {
    Student insertStudent(UserEnteringData userData) throws Exception;
    Student updateStudentInfo(RevisableDataForAdmin userData, String stuNumber) throws Exception;
    Student maintainStudentInfo(RevisableDataForUser userData, String stuNumber) throws Exception;
    Student getByStuNumber(String stuNumber) throws Exception;
}
