package com.example.lab3_behind.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MajorUpdatingData {
    String majorOldName;

    String majorNewName;

    String majorOldSchool;

    String majorNewSchool;

    String introduction;
}
