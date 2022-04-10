package com.example.lab3_behind.service;

import com.example.lab3_behind.domain.Student;
import com.example.lab3_behind.domain.dto.UserMaintenanceData;
import com.example.lab3_behind.domain.dto.UserEnteringData;

public interface StudentService {
    Student insertStudent(UserEnteringData userData) throws Exception;
    Student updateStudentInfo(UserMaintenanceData userData) throws Exception;
}
