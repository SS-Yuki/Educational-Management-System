package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "teaching_building")
    private TeachingBuilding teachingBuilding;

}
